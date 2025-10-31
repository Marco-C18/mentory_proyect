package com.mentory.mentory_proyect.Controller;

import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import com.mentory.mentory_proyect.model.UsuarioBaseModel;
import com.mentory.mentory_proyect.services.AprendizService;
import com.mentory.mentory_proyect.services.MentorService;
import com.mentory.mentory_proyect.services.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AprendizService aprendizService;

    @Autowired
    private MentorService mentorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // ==================== PASO 1: Registro Básico ====================
    
    @GetMapping("/registro")
    public String mostrarRegistroBasico(Model model) {
        model.addAttribute("usuario", new UsuarioBaseModel());
        return "register";
    }

    @PostMapping("/registro")
    public String registrarUsuarioBasico(
            @ModelAttribute("usuario") UsuarioBaseModel usuario,
            Model model,
            HttpServletRequest request) {

        String resultado = usuarioService.registrarUsuarioBasico(usuario);

        if (!resultado.equals("Registro exitoso")) {
            model.addAttribute("error", resultado);
            model.addAttribute("usuario", usuario);
            return "register";
        }

        // Auto-login después del registro
        autoLogin(usuario.getEmailUsuario(), 
                  request.getParameter("contraseñaUsuario"), 
                  request);

        // Redirigir a elegir rol
        return "redirect:/registro/elegir-rol";
    }

    // ==================== PASO 2: Elegir Rol ====================
    
    @GetMapping("/registro/elegir-rol")
    public String mostrarEleccionRol(Authentication auth, Model model) {
        if (auth == null) {
            return "redirect:/login";
        }

        String email = auth.getName();
        UsuarioBaseModel usuario = usuarioService.buscarPorEmail(email);

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "elegir-rol";
    }

    @PostMapping("/registro/elegir-rol")
    public String guardarRol(
            @RequestParam("rol") String rol,
            Authentication auth) {

        if (auth == null) {
            return "redirect:/login";
        }

        String email = auth.getName();
        UsuarioBaseModel usuario = usuarioService.buscarPorEmail(email);

        if (usuario == null) {
            return "redirect:/login";
        }

        // Actualizar rol
        usuarioService.actualizarRol(usuario.getId(), rol);

        // Redirigir según el rol elegido
        if (rol.equals("MENTOR")) {
            return "redirect:/registro/completar-mentor";
        } else {
            return "redirect:/registro/completar-aprendiz";
        }
    }

    // ==================== PASO 3: Completar Perfil MENTOR ====================
    
    @GetMapping("/registro/completar-mentor")
    public String mostrarCompletarMentor(Authentication auth, Model model) {
        if (auth == null) {
            return "redirect:/login";
        }

        String email = auth.getName();
        UsuarioBaseModel usuario = usuarioService.buscarPorEmail(email);

        if (usuario == null || !usuario.getRol().equals("MENTOR")) {
            return "redirect:/registro/elegir-rol";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("mentor", new MentorModel());
        return "register-mentor";
    }

    @PostMapping("/registro/completar-mentor")
    public String completarPerfilMentor(
            @RequestParam("cicloAcademico") String cicloAcademico,
            @RequestParam("especialidad") String especialidad,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        if (auth == null) {
            return "redirect:/login";
        }

        String email = auth.getName();
        UsuarioBaseModel usuario = usuarioService.buscarPorEmail(email);

        if (usuario == null) {
            return "redirect:/login";
        }

        // Crear registro en tabla mentores
        MentorModel mentor = new MentorModel();
        mentor.setNombreUsuario(usuario.getNombreUsuario());
        mentor.setEmailUsuario(usuario.getEmailUsuario());
        mentor.setContraseñaUsuario(usuario.getContraseñaUsuario());
        mentor.setCicloAcademico(cicloAcademico);
        mentor.setEspecialidad(especialidad);
        mentor.setRol("MENTOR");

        // Guardar mentor (sin validaciones porque ya se validó antes)
        mentorService.guardarMentorDirecto(mentor);

        // Marcar perfil como completado
        usuarioService.marcarPerfilCompletado(usuario.getId());

        redirectAttributes.addFlashAttribute("successMessage", "¡Tu perfil de mentor ha sido creado exitosamente!");
        redirectAttributes.addFlashAttribute("showSuccessModal", true);

        return "redirect:/mentor-home";
    }

    // ==================== PASO 3: Completar Perfil APRENDIZ ====================
    
    @GetMapping("/registro/completar-aprendiz")
    public String mostrarCompletarAprendiz(Authentication auth, Model model) {
        if (auth == null) {
            return "redirect:/login";
        }

        String email = auth.getName();
        UsuarioBaseModel usuario = usuarioService.buscarPorEmail(email);

        if (usuario == null || !usuario.getRol().equals("APRENDIZ")) {
            return "redirect:/registro/elegir-rol";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("aprendiz", new AprendizModel());
        return "register-aprendiz";
    }

    @PostMapping("/registro/completar-aprendiz")
    public String completarPerfilAprendiz(
            @RequestParam("cicloAcademico") String cicloAcademico,
            @RequestParam(value = "interesesAprendiz", required = false) List<String> intereses,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        if (auth == null) {
            return "redirect:/login";
        }

        String email = auth.getName();
        UsuarioBaseModel usuario = usuarioService.buscarPorEmail(email);

        if (usuario == null) {
            return "redirect:/login";
        }

        // Crear registro en tabla aprendices
        AprendizModel aprendiz = new AprendizModel();
        aprendiz.setNombreUsuario(usuario.getNombreUsuario());
        aprendiz.setEmailUsuario(usuario.getEmailUsuario());
        aprendiz.setContraseñaUsuario(usuario.getContraseñaUsuario());
        aprendiz.setCicloAcademico(cicloAcademico);
        
        if (intereses != null) {
            aprendiz.setInteresesAprendiz(String.join(",", intereses));
        } else {
            aprendiz.setInteresesAprendiz("");
        }
        
        aprendiz.setRol("APRENDIZ");

        // Guardar aprendiz
        aprendizService.guardarAprendizDirecto(aprendiz);

        // Marcar perfil como completado
        usuarioService.marcarPerfilCompletado(usuario.getId());

        redirectAttributes.addFlashAttribute("successMessage", "¡Tu perfil de aprendiz ha sido creado exitosamente!");
        redirectAttributes.addFlashAttribute("showSuccessModal", true);

        return "redirect:/aprendiz-home";
    }

    // ==================== HELPER: Auto-Login ====================
    
    private void autoLogin(String email, String password, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken token = 
                new UsernamePasswordAuthenticationToken(email, password);
            
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            // Guardar en sesión
            request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
            );
            
        } catch (Exception e) {
            System.err.println("Error en auto-login: " + e.getMessage());
        }
    }
}