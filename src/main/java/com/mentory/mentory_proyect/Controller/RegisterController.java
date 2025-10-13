package com.mentory.mentory_proyect.Controller;

import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import com.mentory.mentory_proyect.services.AprendizService;
import com.mentory.mentory_proyect.services.MentorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    @Autowired
    private AprendizService aprendizService;
    @Autowired
    private MentorService mentorService;

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarModalidad() {
        return "validation";
    }

    @GetMapping("/registro/mentor")
    public String mostrarVistaMentor(Model model) {
        model.addAttribute("mentor", new MentorModel());
        return "register-mentor";
    }

    @PostMapping("/registro/mentor")
    public String registrarMentor(@ModelAttribute("mentor") MentorModel user,
            Model model,
            RedirectAttributes redirectAttributes) {

        String resultado = mentorService.registerUser(user);

        if (resultado.equals("Registro exitoso")) {
            redirectAttributes.addFlashAttribute("successMessage", "¡Tu cuenta ha sido creada exitosamente!");
            redirectAttributes.addFlashAttribute("showSuccessModal", true);
            return "redirect:/login";
        } else {
            model.addAttribute("error", resultado);
            model.addAttribute("mentor", user); // Mantener los datos ingresados
            return "register-mentor";
        }
    }

    @GetMapping("/registro/aprendiz")
    public String mostrarVistaAprendiz(Model model) {
        model.addAttribute("aprendiz", new AprendizModel());
        return "register-aprendiz";
    }

    @PostMapping("/registro/aprendiz")
    public String registrarAprendiz(@ModelAttribute("aprendiz") AprendizModel user,
            Model model,
            @RequestParam(value = "interesesAprendiz", required = false) List<String> intereses,
            RedirectAttributes redirectAttributes) {

        if (intereses != null) {
            user.setInteresesAprendiz(String.join(",", intereses));
        } else {
            user.setInteresesAprendiz("");
        }

        String resultado = aprendizService.registerUser(user);

        if (resultado.equals("Registro exitoso")) {
            redirectAttributes.addFlashAttribute("successMessage", "¡Tu cuenta ha sido creada exitosamente!");
            redirectAttributes.addFlashAttribute("showSuccessModal", true);
            return "redirect:/login";
        } else {
            model.addAttribute("error", resultado);
            model.addAttribute("aprendiz", user); // Mantener los datos ingresados
            return "register-aprendiz";
        }
    }

}