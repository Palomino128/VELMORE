package pe.edu.velmorealt3.repository;
import pe.edu.velmorealt3.model.Categoria;
import pe.edu.velmorealt3.model.Producto;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class ProductoRepository {
    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(4);
    public ProductoRepository() {
        productos.add(new Producto(1L,"Khamrah","Lattafa",Categoria.UNISEX,169,"Fragancia dulce, cálida y especiada.",12,true));
        productos.add(new Producto(2L,"9PM","Afnan",Categoria.MASCULINO,159,"Aroma intenso, juvenil y elegante.",8,true));
        productos.add(new Producto(3L,"Sublime","Lattafa",Categoria.FEMENINO,149,"Fragancia frutal y floral.",10,true));
        productos.add(new Producto(4L,"Club de Nuit Intense","Armaf",Categoria.MASCULINO,175,"Fragancia cítrica y amaderada.",6,true));
    }
    public List<Producto> findAll() { return new ArrayList<>(productos); }
    public Optional<Producto> findById(Long id) { return productos.stream().filter(p -> p.getId().equals(id)).findFirst(); }
    public Producto save(Producto producto) {
        if(producto.getId()==null || producto.getId()==0) { producto.setId(secuencia.incrementAndGet()); productos.add(producto); return producto; }
        for(int i=0;i<productos.size();i++) { if(productos.get(i).getId().equals(producto.getId())) { productos.set(i, producto); return producto; } }
        productos.add(producto); return producto;
    }
    public void deleteById(Long id) { productos.removeIf(p -> p.getId().equals(id)); }
}
