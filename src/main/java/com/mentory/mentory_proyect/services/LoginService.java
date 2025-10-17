package com.mentory.mentory_proyect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.model.MentorModel;
import com.mentory.mentory_proyect.repository.AprendizRepository;
import com.mentory.mentory_proyect.repository.MentorRepository;

@Service
public class LoginService {

    @Autowired
    private AprendizRepository aprendizRepository;

    @Autowired
    private MentorRepository mentorRepository;

    public String obtenerRolUsuario(String email) {
        if (aprendizRepository.existsByEmailUsuario(email)) {
            return "aprendiz";
        } else if (mentorRepository.existsByEmailUsuario(email)) {
            return "mentor";
        } else {
            return "desconocido";
        }
    }
}
