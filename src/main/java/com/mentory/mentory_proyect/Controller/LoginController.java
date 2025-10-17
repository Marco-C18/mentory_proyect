package com.mentory.mentory_proyect.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.mentory.mentory_proyect.services.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/verificar-rol")
    public String verificarRol(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        String rol = loginService.obtenerRolUsuario(email);

        if (rol.equals("aprendiz")) {
            return "redirect:/home-aprendiz";
        } else if (rol.equals("mentor")) {
            return "redirect:/home-mentor";
        } else {
            return "redirect:/login?error=rol";
        }
    }


    
}



