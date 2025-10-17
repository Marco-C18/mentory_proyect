package com.mentory.mentory_proyect.Security;

import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import com.mentory.mentory_proyect.services.AprendizService;
import com.mentory.mentory_proyect.services.MentorService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AprendizService aprendizService;
    private final MentorService mentorService;

    public CustomUserDetailsService(AprendizService aprendizService, MentorService mentorService) {
        this.aprendizService = aprendizService;
        this.mentorService = mentorService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AprendizModel aprendiz = aprendizService.buscarPorEmail(email);
        if (aprendiz != null) {
            return new CustomUserDetails(aprendiz);
        }

        MentorModel mentor = mentorService.buscarPorEmail(email);
        if (mentor != null) {
            return new CustomUserDetails(mentor); 
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + email);
    }
}
