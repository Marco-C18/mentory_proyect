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

        for (GrantedAuthority role : roles) {
            String roleName = role.getAuthority();
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
