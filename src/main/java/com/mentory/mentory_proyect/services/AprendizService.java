package com.mentory.mentory_proyect.services;

import com.mentory.mentory_proyect.model.AprendizModel;
import com.mentory.mentory_proyect.repository.AprendizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AprendizService {

    @Autowired
    private AprendizRepository aprendizRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔹 Obtener aprendiz por ID
    public AprendizModel obtenerPorId(Long id) {
        return aprendizRepository.findById(id).orElse(null);
    }

    // 🔹 Listar todos los aprendices
    public List<AprendizModel> listarTodos() {
        return aprendizRepository.findAll();
    }

    // 🔹 Registrar nuevo aprendiz (con validaciones)
    public String registerUser(AprendizModel user) {
        if (aprendizRepository.existsByEmailUsuario(user.getEmailUsuario())) {
            return "El correo ya está registrado.";
        }

        if (aprendizRepository.existsByPhoneUsuario(user.getPhoneUsuario())) {
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

        aprendizRepository.save(user);
        return "Registro exitoso";
    }

    // 🔹 Buscar aprendiz por email (para autenticación y HomeController)
    public AprendizModel buscarPorEmail(String email) {
        return aprendizRepository.findByEmailUsuario(email).orElse(null);
    }
}
