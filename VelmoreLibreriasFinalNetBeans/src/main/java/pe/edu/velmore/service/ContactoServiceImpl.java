package pe.edu.velmore.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pe.edu.velmore.dto.ContactoDto;
import pe.edu.velmore.model.Producto;

@Service
public class ContactoServiceImpl implements ContactoService {
    private final ProductoService productoService;

    public ContactoServiceImpl(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Override
    public String generarMensaje(ContactoDto dto) {
        Producto producto = productoService.obtenerPorId(dto.getProductoId());
        String cliente = StringUtils.defaultIfBlank(dto.getNombreCliente(), "Cliente");
        int cantidad = dto.getCantidad() <= 0 ? 1 : dto.getCantidad();

        return "Hola, soy " + cliente + ". Estoy interesado en "
                + producto.getNombre() + " de " + producto.getMarca()
                + ". Cantidad solicitada: " + cantidad
                + ". ¿Me brindan más información?";
    }
}
