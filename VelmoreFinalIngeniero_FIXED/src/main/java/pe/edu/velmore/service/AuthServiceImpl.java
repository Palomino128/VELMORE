package pe.edu.velmore.service;

import pe.edu.velmore.dto.LoginDto;
import pe.edu.velmore.model.Usuario;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public Usuario autenticar(LoginDto login) {
        if (login == null) {
            log.warn("Intento de login vacío");
            return null;
        }

        String usuario = StringUtils.trimToEmpty(login.getUsuario());
        String clave = StringUtils.trimToEmpty(login.getClave());

        if (StringUtils.equals(usuario, "admin") && StringUtils.equals(clave, "123456")) {
            log.info("Acceso correcto como ADMIN");
            return new Usuario("admin", "", "Administrador VELMORE", "ADMIN");
        }

        if (StringUtils.equals(usuario, "cliente") && StringUtils.equals(clave, "123456")) {
            log.info("Acceso correcto como CLIENTE");
            return new Usuario("cliente", "", "Cliente VELMORE", "CLIENTE");
        }

        log.warn("Credenciales incorrectas para usuario: {}", usuario);
        return null;
    }
}
