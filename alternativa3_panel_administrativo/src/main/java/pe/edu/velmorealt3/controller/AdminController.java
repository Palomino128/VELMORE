package pe.edu.velmorealt3.controller;
import pe.edu.velmorealt3.dto.LoginDto;
import pe.edu.velmorealt3.model.Categoria;
import pe.edu.velmorealt3.model.Producto;
import pe.edu.velmorealt3.service.AuthService;
import pe.edu.velmorealt3.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class AdminController {
    private final ProductoService productoService; private final AuthService authService;
    public AdminController(ProductoService productoService, AuthService authService) { this.productoService=productoService; this.authService=authService; }
    @GetMapping("/login") public String login(Model model) { model.addAttribute("login",new LoginDto()); return "login"; }
    @PostMapping("/login") public String procesarLogin(@ModelAttribute LoginDto login, Model model) {
        if(!authService.validar(login)) { model.addAttribute("error","Credenciales incorrectas"); return "login"; }
        return "redirect:/admin";
    }
    @GetMapping("/admin") public String admin(Model model) { cargar(model,new Producto()); model.addAttribute("admin",authService.obtenerAdministrador()); return "admin"; }
    @PostMapping("/admin/productos") public String registrar(@ModelAttribute Producto producto, Model model) {
        try { productoService.registrar(producto); return "redirect:/admin"; }
        catch(IllegalArgumentException ex) { model.addAttribute("error",ex.getMessage()); cargar(model,producto); return "admin"; }
    }
    @PostMapping("/admin/productos/{id}/editar") public String editar(@PathVariable Long id, @ModelAttribute Producto producto, Model model) {
        try { productoService.actualizar(id,producto); return "redirect:/admin"; }
        catch(IllegalArgumentException ex) { model.addAttribute("error",ex.getMessage()); cargar(model,producto); return "admin"; }
    }
    @PostMapping("/admin/productos/{id}/eliminar") public String eliminar(@PathVariable Long id) { productoService.eliminar(id); return "redirect:/admin"; }
    private void cargar(Model model, Producto producto) {
        model.addAttribute("productos",productoService.listarActivos()); model.addAttribute("producto",producto);
        model.addAttribute("categorias",Categoria.values()); model.addAttribute("totalProductos",productoService.totalProductos());
        model.addAttribute("totalCategorias",productoService.totalCategorias()); model.addAttribute("valorInventario",productoService.valorInventario());
    }
}
