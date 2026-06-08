package pe.edu.velmore.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.edu.velmore.dao.ProductoDao;
import pe.edu.velmore.dto.BusquedaProductoDto;
import pe.edu.velmore.model.Categoria;
import pe.edu.velmore.model.Producto;
import pe.edu.velmore.validation.ProductoValidator;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {
    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoDao productoDao;
    private final ProductoValidator productoValidator;
    private final Cache<String, List<Producto>> cacheProductos;

    public ProductoServiceImpl(ProductoDao productoDao, ProductoValidator productoValidator) {
        this.productoDao = productoDao;
        this.productoValidator = productoValidator;
        this.cacheProductos = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
    }

    @Override
    public List<Producto> listarActivos() {
        List<Producto> enCache = cacheProductos.getIfPresent("activos");
        if (enCache != null) {
            log.info("Catálogo obtenido desde cache Guava");
            return enCache;
        }

        List<Producto> activos = productoDao.findAll().stream()
                .filter(Producto::isActivo)
                .sorted(Comparator.comparing(Producto::getNombre))
                .collect(Collectors.toList());

        cacheProductos.put("activos", activos);
        log.info("Catálogo cargado desde DAO y guardado en cache");
        return activos;
    }

    @Override
    public List<Producto> buscar(BusquedaProductoDto filtro) {
        String texto = filtro == null ? "" : StringUtils.trimToEmpty(filtro.getTexto()).toLowerCase();
        Categoria categoria = filtro == null ? null : filtro.getCategoria();

        return listarActivos().stream()
                .filter(producto -> StringUtils.isBlank(texto)
                        || producto.getNombre().toLowerCase().contains(texto)
                        || producto.getMarca().toLowerCase().contains(texto))
                .filter(producto -> categoria == null || producto.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    @Override
    public Producto registrar(Producto producto) {
        productoValidator.validar(producto);
        producto.setActivo(true);
        Producto registrado = productoDao.save(producto);
        cacheProductos.invalidateAll();
        log.info("Cache limpiado luego de registrar producto");
        return registrado;
    }

    @Override
    public void eliminar(Long id) {
        productoDao.deleteById(id);
        cacheProductos.invalidateAll();
    }

    @Override
    public int totalProductos() {
        return listarActivos().size();
    }

    @Override
    public long totalCategorias() {
        return listarActivos().stream().map(Producto::getCategoria).distinct().count();
    }

    @Override
    public double valorInventario() {
        return listarActivos().stream().mapToDouble(producto -> producto.getPrecio() * producto.getStock()).sum();
    }
}
