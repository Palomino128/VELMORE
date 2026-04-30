package pe.edu.velmorealt1.dto;
import pe.edu.velmorealt1.model.Categoria;
public class BusquedaProductoDto {
    private String texto;
    private Categoria categoria;
    public String getTexto() { return texto; } public void setTexto(String texto) { this.texto=texto; }
    public Categoria getCategoria() { return categoria; } public void setCategoria(Categoria categoria) { this.categoria=categoria; }
}
