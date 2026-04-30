package pe.edu.velmorealt3.service;
import pe.edu.velmorealt3.dto.ContactoDto;
import pe.edu.velmorealt3.model.Producto;
import org.springframework.stereotype.Service;
@Service
public class ContactoService {
    private final ProductoService productoService;
    public ContactoService(ProductoService productoService) { this.productoService=productoService; }
    public String generarMensaje(ContactoDto dto) {
        Producto p = productoService.obtenerPorId(dto.getProductoId());
        String cliente = dto.getNombreCliente()==null || dto.getNombreCliente().isBlank() ? "Cliente" : dto.getNombreCliente();
        int cantidad = dto.getCantidad() <= 0 ? 1 : dto.getCantidad();
        return "Hola, soy " + cliente + ". Deseo información sobre " + p.getNombre() + " de " + p.getMarca() + ". Cantidad solicitada: " + cantidad + ".";
    }
}
