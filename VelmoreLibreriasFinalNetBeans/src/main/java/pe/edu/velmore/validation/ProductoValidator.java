package pe.edu.velmore.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pe.edu.velmore.model.Producto;

@Component
public class ProductoValidator {
    public void validar(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (StringUtils.isBlank(producto.getNombre())) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (StringUtils.isBlank(producto.getMarca())) {
            throw new IllegalArgumentException("La marca es obligatoria");
        }
        if (producto.getCategoria() == null) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }

    public String limpiarTexto(String texto) {
        return StringUtils.trimToEmpty(texto);
    }
}
