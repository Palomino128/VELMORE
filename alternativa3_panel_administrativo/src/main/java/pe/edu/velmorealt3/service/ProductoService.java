package pe.edu.velmorealt3.service;
import pe.edu.velmorealt3.model.Producto;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();
    public ProductoService(){
        productos.add(new Producto(1L,"Khamrah","Lattafa","Unisex",169,"Fragancia dulce, cálida y especiada."));
        productos.add(new Producto(2L,"9PM","Afnan","Masculino",159,"Aroma intenso, juvenil y elegante."));
        productos.add(new Producto(3L,"Sublime","Lattafa","Femenino",149,"Fragancia frutal y floral."));
        productos.add(new Producto(4L,"Club de Nuit Intense","Armaf","Masculino",175,"Fragancia cítrica y amaderada."));
    }
    public List<Producto> listar(){return productos;}
    public Optional<Producto> obtener(Long id){return productos.stream().filter(p->p.getId().equals(id)).findFirst();}
    public List<Producto> buscar(String q,String categoria){
        return productos.stream()
        .filter(p -> q==null || q.isBlank() || p.getNombre().toLowerCase().contains(q.toLowerCase()) || p.getMarca().toLowerCase().contains(q.toLowerCase()))
        .filter(p -> categoria==null || categoria.isBlank() || p.getCategoria().equalsIgnoreCase(categoria))
        .collect(Collectors.toList());
    }
    public void registrar(Producto p){
        long id = productos.stream().mapToLong(Producto::getId).max().orElse(0)+1;
        p.setId(id); productos.add(p);
    }
    public void eliminar(Long id){ productos.removeIf(p -> p.getId().equals(id)); }
}
