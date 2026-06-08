package pe.edu.velmore.service;

import org.junit.jupiter.api.Test;
import pe.edu.velmore.dao.InMemoryProductoDao;
import pe.edu.velmore.dto.BusquedaProductoDto;
import pe.edu.velmore.model.Categoria;
import pe.edu.velmore.model.Producto;
import pe.edu.velmore.validation.ProductoValidator;

import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceImplTest {
    private ProductoService crearService() {
        return new ProductoServiceImpl(new InMemoryProductoDao(), new ProductoValidator());
    }

    @Test
    void buscarPorNombreDebeRetornarProductosCoincidentes() {
        ProductoService service = crearService();
        BusquedaProductoDto filtro = new BusquedaProductoDto();
        filtro.setTexto("Khamrah");
        assertFalse(service.buscar(filtro).isEmpty());
    }

    @Test
    void registrarProductoConPrecioInvalidoDebeLanzarError() {
        ProductoService service = crearService();
        Producto producto = new Producto(null, "Prueba", "Marca", Categoria.UNISEX, 0, "Descripción", 1, true);
        assertThrows(IllegalArgumentException.class, () -> service.registrar(producto));
    }
}
