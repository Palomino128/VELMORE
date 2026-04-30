package pe.edu.velmorealt2.model;
public class Administrador {
    private String usuario;
    private String nombre;
    private String rol;
    public Administrador(String usuario, String nombre, String rol) { this.usuario=usuario; this.nombre=nombre; this.rol=rol; }
    public String getUsuario() { return usuario; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }
}
