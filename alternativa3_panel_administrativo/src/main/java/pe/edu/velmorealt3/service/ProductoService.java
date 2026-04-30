package pe.edu.velmorealt3.service;
import pe.edu.velmorealt3.dto.BusquedaProductoDto;
import pe.edu.velmorealt3.model.Categoria;
import pe.edu.velmorealt3.model.Producto;
import pe.edu.velmorealt3.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductoService {
    private final ProductoRepository repository;
    public ProductoService(ProductoRepository repository) { this.repository=repository; }
    public List<Producto> listarActivos() {
        return repository.findAll().stream().filter(Producto::isActivo).sorted(Comparator.comparing(Producto::getNombre)).collect(Collectors.toList());
    }
    public List<Producto> buscar(BusquedaProductoDto filtro) {
        String texto = filtro == null ? null : filtro.getTexto();
        Categoria categoria = filtro == null ? null : filtro.getCategoria();
        return listarActivos().stream()
            .filter(p -> texto == null || texto.isBlank() || p.getNombre().toLowerCase().contains(texto.toLowerCase()) || p.getMarca().toLowerCase().contains(texto.toLowerCase()))
            .filter(p -> categoria == null || p.getCategoria() == categoria)
            .collect(Collectors.toList());
    }
    public Producto obtenerPorId(Long id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado")); }
    public Producto registrar(Producto producto) { validar(producto); producto.setActivo(true); return repository.save(producto); }
    public Producto actualizar(Long id, Producto datos) {
        Producto actual = obtenerPorId(id);
        actual.setNombre(datos.getNombre()); actual.setMarca(datos.getMarca()); actual.setCategoria(datos.getCategoria());
        actual.setPrecio(datos.getPrecio()); actual.setDescripcion(datos.getDescripcion()); actual.setStock(datos.getStock());
        validar(actual); return repository.save(actual);
    }
    public void eliminar(Long id) { repository.deleteById(id); }
    public int totalProductos() { return listarActivos().size(); }
    public long totalCategorias() { return listarActivos().stream().map(Producto::getCategoria).distinct().count(); }
    public double valorInventario() { return listarActivos().stream().mapToDouble(p -> p.getPrecio() * p.getStock()).sum(); }
    private void validar(Producto p) {
        if(p.getNombre()==null || p.getNombre().isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        if(p.getMarca()==null || p.getMarca().isBlank()) throw new IllegalArgumentException("La marca es obligatoria");
        if(p.getCategoria()==null) throw new IllegalArgumentException("La categoría es obligatoria");
        if(p.getPrecio()<=0) throw new IllegalArgumentException("El precio debe ser mayor a cero");
        if(p.getStock()<0) throw new IllegalArgumentException("El stock no puede ser negativo");
    }
}
