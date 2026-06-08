package pe.edu.velmore.service;

import pe.edu.velmore.dto.BusquedaProductoDto;
import pe.edu.velmore.model.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> listarActivos();
    List<Producto> buscar(BusquedaProductoDto filtro);
    Producto obtenerPorId(Long id);
    Producto registrar(Producto producto);
    void eliminar(Long id);
    int totalProductos();
    long totalCategorias();
    double valorInventario();
}
