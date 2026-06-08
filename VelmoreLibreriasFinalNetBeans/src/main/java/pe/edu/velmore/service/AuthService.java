package pe.edu.velmore.service;

import pe.edu.velmore.dto.LoginDto;

public interface AuthService {
    boolean validar(LoginDto login);
}
