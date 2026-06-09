package pe.edu.velmore.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
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

/**
 * Implementación del servicio de productos.
 * Usa Google Guava para caché en memoria (CacheBuilder) e ImmutableList.
 * Usa Apache Commons Lang para validaciones de texto seguras.
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);
    private static final String CACHE_KEY_ACTIVOS = "activos";

    private final ProductoDao productoDao;
    private final ProductoValidator productoValidator;
    private final Cache<String, List<Producto>> cacheProductos;

    public ProductoServiceImpl(ProductoDao productoDao, ProductoValidator productoValidator) {
        this.productoDao = productoDao;
        this.productoValidator = productoValidator;
        this.cacheProductos = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .maximumSize(100)
                .recordStats()
                .build();
    }

    @Override
    public List<Producto> listarActivos() {
        List<Producto> enCache = cacheProductos.getIfPresent(CACHE_KEY_ACTIVOS);
        if (enCache != null) {
            log.info("Catálogo obtenido desde caché Guava. Stats: {}", cacheProductos.stats());
            return enCache;
        }

        List<Producto> activos = productoDao.findAll().stream()
                .filter(Producto::isActivo)
                .sorted(Comparator.comparing(Producto::getNombre))
                .collect(Collectors.toList());

        cacheProductos.put(CACHE_KEY_ACTIVOS, ImmutableList.copyOf(activos));
        log.info("Catálogo cargado desde DAO ({} productos) y guardado en caché", activos.size());
        return activos;
    }

    @Override
    public List<Producto> buscar(BusquedaProductoDto filtro) {
        String texto = filtro == null ? "" : StringUtils.trimToEmpty(filtro.getTexto()).toLowerCase();
        Categoria categoria = filtro == null ? null : filtro.getCategoria();

        return listarActivos().stream()
                .filter(p -> StringUtils.isBlank(texto)
                        || p.getNombre().toLowerCase().contains(texto)
                        || p.getMarca().toLowerCase().contains(texto)
                        || p.getDescripcion().toLowerCase().contains(texto))
                .filter(p -> categoria == null || p.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
    }

    @Override
    public Producto registrar(Producto producto) {
        productoValidator.validar(producto);
        producto.setNombre(StringUtils.trimToEmpty(producto.getNombre()));
        producto.setMarca(StringUtils.trimToEmpty(producto.getMarca()));
        producto.setActivo(true);
        Producto registrado = productoDao.save(producto);
        cacheProductos.invalidateAll();
        log.info("Producto registrado: {} (ID: {})", registrado.getNombre(), registrado.getId());
        return registrado;
    }

    @Override
    public Producto actualizar(Long id, Producto producto) {
        // Verificar que el producto existe
        obtenerPorId(id);
        producto.setId(id);
        producto.setNombre(StringUtils.trimToEmpty(producto.getNombre()));
        producto.setMarca(StringUtils.trimToEmpty(producto.getMarca()));
        producto.setActivo(true);
        productoValidator.validar(producto);
        Producto actualizado = productoDao.save(producto);
        cacheProductos.invalidateAll();
        log.info("Producto actualizado: {} (ID: {})", actualizado.getNombre(), actualizado.getId());
        return actualizado;
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id); // valida que existe antes de eliminar
        productoDao.deleteById(id);
        cacheProductos.invalidateAll();
        log.warn("Producto eliminado con ID: {}", id);
    }

    @Override
    public int totalProductos() {
        return listarActivos().size();
    }

    @Override
    public long totalCategorias() {
        return listarActivos().stream()
                .map(Producto::getCategoria)
                .distinct()
                .count();
    }

    @Override
    public double valorInventario() {
        return listarActivos().stream()
                .mapToDouble(p -> p.getPrecio() * p.getStock())
                .sum();
    }
}
