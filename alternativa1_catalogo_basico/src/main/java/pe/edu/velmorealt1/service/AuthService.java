package pe.edu.velmorealt1.service;
import pe.edu.velmorealt1.dto.LoginDto;
import pe.edu.velmorealt1.model.Administrador;
import org.springframework.stereotype.Service;
@Service
public class AuthService {
    public boolean validar(LoginDto login) { return login != null && "admin".equals(login.getUsuario()) && "123456".equals(login.getClave()); }
    public Administrador obtenerAdministrador() { return new Administrador("admin","Administrador VELMORE","ADMIN"); }
}
