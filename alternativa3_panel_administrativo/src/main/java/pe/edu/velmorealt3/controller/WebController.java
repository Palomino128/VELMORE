package pe.edu.velmorealt3.controller;
import pe.edu.velmorealt3.model.Producto;
import pe.edu.velmorealt3.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class WebController {
    private final ProductoService service;
    public WebController(ProductoService service){this.service=service;}
    @GetMapping("/") public String inicio(Model model){model.addAttribute("titulo","Alternativa 3: Sistema Web con Panel Administrativo");return "inicio";}
    @GetMapping("/catalogo") public String catalogo(@RequestParam(required=false) String q,@RequestParam(required=false) String categoria,Model model){model.addAttribute("productos",service.buscar(q,categoria));return "catalogo";}
    @GetMapping("/producto/{id}") public String detalle(@PathVariable Long id,Model model){model.addAttribute("producto",service.obtener(id).orElseThrow());return "detalle";}
    @GetMapping("/contacto/{id}") public String contacto(@PathVariable Long id,Model model){model.addAttribute("producto",service.obtener(id).orElseThrow());return "contacto";}
    @GetMapping("/admin") public String admin(Model model){model.addAttribute("productos",service.listar());model.addAttribute("producto",new Producto(0L,"","","Unisex",0,""));return "admin";}
    @PostMapping("/admin/productos") public String registrar(@ModelAttribute Producto producto){service.registrar(producto);return "redirect:/admin";}
    @PostMapping("/admin/productos/{id}/eliminar") public String eliminar(@PathVariable Long id){service.eliminar(id);return "redirect:/admin";}
}
