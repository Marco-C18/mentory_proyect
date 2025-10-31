package com.mentory.mentory_proyect.services;

import com.mentory.mentory_proyect.model.UsuarioBaseModel;
import com.mentory.mentory_proyect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registra un usuario básico (PASO 1)
     */
    public String registrarUsuarioBasico(UsuarioBaseModel usuario) {
        
        // Validación: Email ya existe
        if (usuarioRepository.existsByEmailUsuario(usuario.getEmailUsuario())) {
            return "El correo ya está registrado.";
        }

        // Validación: Nombre solo letras
        if (!usuario.getNombreUsuario().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            return "El nombre solo puede contener letras.";
        }


        // Validación: Contraseña mínimo 8 caracteres
        if (usuario.getContraseñaUsuario().length() < 8) {
            return "La contraseña debe tener al menos 8 caracteres.";
        }

        // Cifrar contraseña
        usuario.setContraseñaUsuario(passwordEncoder.encode(usuario.getContraseñaUsuario()));
        
        // Establecer valores por defecto
        usuario.setRol("PENDIENTE");
        usuario.setPerfilCompletado(false);

        // Guardar
        usuarioRepository.save(usuario);
        
        return "Registro exitoso";
    }

    /**
     * Actualiza el rol del usuario (PASO 2)
     */
    public void actualizarRol(Long id, String rol) {
        UsuarioBaseModel usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setRol(rol);
            usuarioRepository.save(usuario);
        }
    }

    /**
     * Marca el perfil como completado
     */
    public void marcarPerfilCompletado(Long id) {
        UsuarioBaseModel usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setPerfilCompletado(true);
            usuarioRepository.save(usuario);
        }
    }

    /**
     * Buscar usuario por email
     */
    public UsuarioBaseModel buscarPorEmail(String email) {
        return usuarioRepository.findByEmailUsuario(email).orElse(null);
    }

    /**
     * Buscar usuario por ID
     */
    public UsuarioBaseModel buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void guardarUsuarioOAuth(UsuarioBaseModel usuario) {
    // Verificar si ya existe
    if (!usuarioRepository.existsByEmailUsuario(usuario.getEmailUsuario())) {
        usuarioRepository.save(usuario);
        System.out.println("✅ Usuario OAuth guardado: " + usuario.getEmailUsuario());
    }
}
}