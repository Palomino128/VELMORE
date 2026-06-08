package pe.edu.velmore.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.edu.velmore.dto.LoginDto;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public boolean validar(LoginDto login) {
        boolean correcto = login != null
                && StringUtils.equals(login.getUsuario(), "admin")
                && StringUtils.equals(login.getClave(), "123456");

        if (correcto) {
            log.info("Acceso administrativo correcto");
        } else {
            log.warn("Intento de acceso administrativo incorrecto");
        }

        return correcto;
    }
}
