package pe.edu.velmorealt2.model;
public class Producto {
    private Long id;
    private String nombre;
    private String marca;
    private Categoria categoria;
    private double precio;
    private String descripcion;
    private int stock;
    private boolean activo;
    public Producto() {}
    public Producto(Long id, String nombre, String marca, Categoria categoria, double precio, String descripcion, int stock, boolean activo) {
        this.id=id; this.nombre=nombre; this.marca=marca; this.categoria=categoria; this.precio=precio; this.descripcion=descripcion; this.stock=stock; this.activo=activo;
    }
    public Long getId() { return id; } public void setId(Long id) { this.id=id; }
    public String getNombre() { return nombre; } public void setNombre(String nombre) { this.nombre=nombre; }
    public String getMarca() { return marca; } public void setMarca(String marca) { this.marca=marca; }
    public Categoria getCategoria() { return categoria; } public void setCategoria(Categoria categoria) { this.categoria=categoria; }
    public double getPrecio() { return precio; } public void setPrecio(double precio) { this.precio=precio; }
    public String getDescripcion() { return descripcion; } public void setDescripcion(String descripcion) { this.descripcion=descripcion; }
    public int getStock() { return stock; } public void setStock(int stock) { this.stock=stock; }
    public boolean isActivo() { return activo; } public void setActivo(boolean activo) { this.activo=activo; }
}
