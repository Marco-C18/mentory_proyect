package com.mentory.mentory_proyect.Controller;

import com.mentory.mentory_proyect.model.User;
import com.mentory.mentory_proyect.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Mostrar formulario de registro
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Procesar registro de usuario
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("user") User user,
            Model model,
            RedirectAttributes redirectAttributes) {
        String resultado = userService.registerUser(user);

        if (resultado.equals("Registro exitoso")) {
            // ✅ Si el registro fue exitoso, redirige y muestra mensaje de éxito
            redirectAttributes.addFlashAttribute("successMessage", "¡Tu cuenta ha sido creada exitosamente!");
            redirectAttributes.addFlashAttribute("showSuccessModal", true);
            return "redirect:/register";
        } else {
            // ⚠️ Si hubo error, se mantiene en la vista actual con el mensaje
            model.addAttribute("error", resultado);
            model.addAttribute("user", user); // Mantener los datos ingresados
            return "register";
        }
    }
}