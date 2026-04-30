package pe.edu.velmorealt2.controller;
import pe.edu.velmorealt2.dto.BusquedaProductoDto;
import pe.edu.velmorealt2.dto.ContactoDto;
import pe.edu.velmorealt2.model.Categoria;
import pe.edu.velmorealt2.service.ContactoService;
import pe.edu.velmorealt2.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class CatalogoController {
    private final ProductoService productoService;
    private final ContactoService contactoService;
    public CatalogoController(ProductoService productoService, ContactoService contactoService) { this.productoService=productoService; this.contactoService=contactoService; }
    @GetMapping("/") public String inicio(Model model) {
        model.addAttribute("titulo","Alternativa 2: Sistema Web Tipo Boutique Digital");
        model.addAttribute("totalProductos",productoService.totalProductos());
        model.addAttribute("totalCategorias",productoService.totalCategorias());
        model.addAttribute("valorInventario",productoService.valorInventario());
        return "inicio";
    }
    @GetMapping("/catalogo") public String catalogo(@ModelAttribute BusquedaProductoDto filtro, Model model) {
        model.addAttribute("productos",productoService.buscar(filtro)); model.addAttribute("categorias",Categoria.values()); model.addAttribute("filtro",filtro); return "catalogo";
    }
    @GetMapping("/producto/{id}") public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("producto",productoService.obtenerPorId(id)); return "detalle";
    }
    @GetMapping("/contacto/{id}") public String contacto(@PathVariable Long id, Model model) {
        model.addAttribute("producto",productoService.obtenerPorId(id)); model.addAttribute("contacto",new ContactoDto()); return "contacto";
    }
    @PostMapping("/contacto/mensaje") public String generarMensaje(@ModelAttribute ContactoDto contacto, Model model) {
        model.addAttribute("producto",productoService.obtenerPorId(contacto.getProductoId()));
        model.addAttribute("mensaje",contactoService.generarMensaje(contacto));
        model.addAttribute("contacto",contacto);
        return "contacto";
    }
}
