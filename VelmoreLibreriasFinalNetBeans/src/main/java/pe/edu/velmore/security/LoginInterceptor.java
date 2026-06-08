package pe.edu.velmore.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        Object admin = request.getSession().getAttribute(SecurityConstants.ADMIN_SESSION);
        if (admin == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
