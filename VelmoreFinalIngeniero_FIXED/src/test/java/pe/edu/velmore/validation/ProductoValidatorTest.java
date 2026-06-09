package pe.edu.velmore.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.velmore.model.Categoria;
import pe.edu.velmore.model.Producto;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para ProductoValidator.
 * Verifica el uso correcto de Google Guava Preconditions y Apache Commons StringUtils.
 */
@DisplayName("Pruebas de ProductoValidator")
class ProductoValidatorTest {

    private final ProductoValidator validator = new ProductoValidator();

    @Test
    @DisplayName("Producto nulo debe lanzar NullPointerException via Guava Preconditions")
    void productoNuloLanzaExcepcion() {
        assertThrows(NullPointerException.class, () -> validator.validar(null));
    }

    @Test
    @DisplayName("Producto válido no debe lanzar ninguna excepción")
    void productoValidoNoLanzaExcepcion() {
        Producto p = new Producto(null, "Nombre", "Marca",
                Categoria.UNISEX, 100, "Descripción de prueba", 5, true);
        assertDoesNotThrow(() -> validator.validar(p));
    }

    @Test
    @DisplayName("limpiarTexto debe eliminar espacios al inicio y al final")
    void limpiarTextoEliminaEspacios() {
        assertEquals("texto limpio", validator.limpiarTexto("  texto limpio  "));
    }

    @Test
    @DisplayName("limpiarTexto con null debe retornar cadena vacía")
    void limpiarTextoConNullRetornaVacio() {
        assertEquals("", validator.limpiarTexto(null));
    }
}
