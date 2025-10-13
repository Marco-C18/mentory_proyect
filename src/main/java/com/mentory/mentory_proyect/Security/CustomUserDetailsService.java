package com.mentory.mentory_proyect.Security;

import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import com.mentory.mentory_proyect.repository.AprendizRepository;
import com.mentory.mentory_proyect.repository.MentorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AprendizRepository aprendizRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar en aprendices
        if (aprendizRepository.findByEmailUsuario(email).isPresent()) {
            AprendizModel aprendiz = aprendizRepository.findByEmailUsuario(email).get();
            return new CustomUserDetails(aprendiz);
        }

        // Buscar en mentores
        if (mentorRepository.findByEmailUsuario(email).isPresent()) {
            MentorModel mentor = mentorRepository.findByEmailUsuario(email).get();
            return new CustomUserDetailsMentor(mentor);
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + email);
    }
}