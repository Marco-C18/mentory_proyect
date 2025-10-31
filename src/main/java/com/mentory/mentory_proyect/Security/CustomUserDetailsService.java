package com.mentory.mentory_proyect.Security;

import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import com.mentory.mentory_proyect.model.UsuarioBaseModel;
import com.mentory.mentory_proyect.services.AprendizService;
import com.mentory.mentory_proyect.services.MentorService;
import com.mentory.mentory_proyect.services.UsuarioService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AprendizService aprendizService;
    private final MentorService mentorService;
    private final UsuarioService usuarioService;

    public CustomUserDetailsService(AprendizService aprendizService, 
                                   MentorService mentorService,
                                   UsuarioService usuarioService) {
        this.aprendizService = aprendizService;
        this.mentorService = mentorService;
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        // 1. Buscar en aprendices
        AprendizModel aprendiz = aprendizService.buscarPorEmail(email);
        if (aprendiz != null) {
            return new CustomUserDetails(aprendiz);
        }

        // 2. Buscar en mentores
        MentorModel mentor = mentorService.buscarPorEmail(email);
        if (mentor != null) {
            return new CustomUserDetails(mentor);
        }

        // 3. Buscar en usuarios base (los que aún no completan perfil)
        UsuarioBaseModel usuario = usuarioService.buscarPorEmail(email);
        if (usuario != null) {
            return new CustomUserDetails(usuario);
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + email);
    }
}