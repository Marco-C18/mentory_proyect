package com.mentory.mentory_proyect.services;

import com.mentory.mentory_proyect.model.MentorModel;
import com.mentory.mentory_proyect.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔹 Obtener mentor por ID
    public MentorModel obtenerPorId(Long id) {
        return mentorRepository.findById(id).orElse(null);
    }

    // 🔹 Listar todos los mentores
    public List<MentorModel> listarTodos() {
        return mentorRepository.findAll();
    }

    // 🔹 Registrar nuevo mentor (con validaciones)
    public String registerUser(MentorModel user) {
        if (mentorRepository.existsByEmailUsuario(user.getEmailUsuario())) {
            return "El correo ya está registrado.";
        }

        if (mentorRepository.existsByPhoneUsuario(user.getPhoneUsuario())) {
            return "El teléfono ya está registrado.";
        }

        if (!user.getNombreUsuario().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            return "El nombre solo puede contener letras.";
        }

        if (!user.getPhoneUsuario().matches("\\d{9}")) {
            return "El teléfono debe tener 9 dígitos numéricos.";
        }

        if (user.getContraseñaUsuario().length() < 8) {
            return "La contraseña debe tener al menos 8 caracteres.";
        }

        // 🔹 Cifrar contraseña antes de guardar
        user.setContraseñaUsuario(passwordEncoder.encode(user.getContraseñaUsuario()));

        mentorRepository.save(user);
        return "Registro exitoso";
    }

    // 🔹 Buscar mentor por email (para autenticación y HomeController)
    public MentorModel buscarPorEmail(String email) {
        return mentorRepository.findByEmailUsuario(email).orElse(null);
    }

    public void guardarMentorDirecto(MentorModel mentor) {
    mentorRepository.save(mentor);
    }
}
