package com.mentory.mentory_proyect.services;

import com.mentory.mentory_proyect.model.User;
import com.mentory.mentory_proyect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(User user) {
        if (userRepository.existsByEmailUsuario(user.getEmailUsuario())) {
            return "El correo ya está registrado.";
        }

        if (userRepository.existsByPhoneUsuario(user.getPhoneUsuario())) {
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

        // Cifrar la contraseña antes de guardar
        user.setContraseñaUsuario(passwordEncoder.encode(user.getContraseñaUsuario()));

        userRepository.save(user);
        return "Registro exitoso";
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailUsuario(email).orElse(null);
    }
}