package com.mentory.mentory_proyect.Security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        String redirectURL = request.getContextPath();

        // Obtener información del usuario
        Object principal = authentication.getPrincipal();
        Boolean perfilCompletado = true;

        if (principal instanceof CustomUserDetails customUser) {
            perfilCompletado = customUser.getPerfilCompletado();
        }

        for (GrantedAuthority role : roles) {
            String roleName = role.getAuthority();
            
            // Si el perfil no está completado, redirigir a elegir rol
            if (perfilCompletado != null && !perfilCompletado) {
                redirectURL = "/registro/elegir-rol";
                break;
            }
            
            // Si el rol es PENDIENTE, redirigir a elegir rol
            if (roleName.equals("ROLE_PENDIENTE")) {
                redirectURL = "/registro/elegir-rol";
                break;
            }
            
            // Rutas normales según rol
            if (roleName.equals("ROLE_MENTOR")) {
                redirectURL = "/mentor-home";
            } else if (roleName.equals("ROLE_APRENDIZ")) {
                redirectURL = "/aprendiz-home";
            }
        }

        // Por defecto aprendiz si no hay roles definidos
        if (redirectURL.equals(request.getContextPath())) {
            redirectURL = "/aprendiz-home";
        }

        System.out.println("➡️ Redirigiendo a: " + redirectURL);
        response.sendRedirect(redirectURL);
    }
}