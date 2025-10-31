package com.mentory.mentory_proyect.Controller;

import com.mentory.mentory_proyect.model.UsuarioBaseModel;
import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import com.mentory.mentory_proyect.services.UsuarioService;
import com.mentory.mentory_proyect.services.AprendizService;
import com.mentory.mentory_proyect.services.MentorService;
import com.mentory.mentory_proyect.Security.CustomUserDetails;
import com.mentory.mentory_proyect.Security.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AprendizService aprendizService;

    @Autowired
    private MentorService mentorService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/google")
    public ResponseEntity<Map<String, Object>> googleAuth(@RequestBody Map<String, String> payload, 
                                                           HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String email = payload.get("email");
            String nombre = payload.get("nombre");

            System.out.println("📧 Login con Google: " + email);
            System.out.println("👤 Nombre: " + nombre);

            String redirectUrl = null;
            boolean esNuevoUsuario = false;

            // 1. Verificar si existe como aprendiz
            AprendizModel aprendiz = aprendizService.buscarPorEmail(email);
            if (aprendiz != null) {
                System.out.println("✅ Usuario encontrado como APRENDIZ");
                redirectUrl = "/aprendiz-home";
            }

            // 2. Verificar si existe como mentor
            if (redirectUrl == null) {
                MentorModel mentor = mentorService.buscarPorEmail(email);
                if (mentor != null) {
                    System.out.println("✅ Usuario encontrado como MENTOR");
                    redirectUrl = "/mentor-home";
                }
            }

            // 3. Verificar si existe en usuarios base
            if (redirectUrl == null) {
                UsuarioBaseModel usuarioBase = usuarioService.buscarPorEmail(email);
                
                if (usuarioBase != null) {
                    System.out.println("✅ Usuario encontrado en tabla USUARIOS");
                    
                    if (!usuarioBase.getPerfilCompletado()) {
                        redirectUrl = "/registro/elegir-rol";
                    } else {
                        redirectUrl = usuarioBase.getRol().equals("MENTOR") ? "/mentor-home" : "/aprendiz-home";
                    }
                } else {
                    // 4. Usuario nuevo → crear en tabla usuarios
                    System.out.println("🆕 Creando nuevo usuario OAuth");
                    
                    UsuarioBaseModel nuevoUsuario = new UsuarioBaseModel();
                    nuevoUsuario.setNombreUsuario(nombre);
                    nuevoUsuario.setEmailUsuario(email);
                    nuevoUsuario.setContraseñaUsuario("GOOGLE_OAUTH");
                    nuevoUsuario.setRol("PENDIENTE");
                    nuevoUsuario.setPerfilCompletado(false);

                    usuarioService.guardarUsuarioOAuth(nuevoUsuario);
                    
                    esNuevoUsuario = true;
                    redirectUrl = "/registro/elegir-rol";
                }
            }

            // Realizar auto-login usando CustomUserDetailsService
            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);

            // Crear token autenticado
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(
                    userDetails, 
                    userDetails.getPassword(), 
                    userDetails.getAuthorities()
                );

            // Marcar como autenticado
            // authentication.setAuthenticated(true);

            // Establecer en SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Guardar en sesión HTTP
            HttpSession session = request.getSession(true);
            session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
            );

            System.out.println("✅ Auto-login exitoso");
            System.out.println("🔐 Roles: " + userDetails.getAuthorities());
            System.out.println("➡️ Redirigiendo a: " + redirectUrl);

            response.put("success", true);
            response.put("redirectUrl", redirectUrl);
            response.put("isNewUser", esNuevoUsuario);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Error en Google Auth: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
