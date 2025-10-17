package com.mentory.mentory_proyect.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreUsuario;
    private String emailUsuario;
    private String phoneUsuario;
    private String contraseñaUsuario;
    private String rol;

    // Getters y setters manuales
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }

    public String getPhoneUsuario() { return phoneUsuario; }
    public void setPhoneUsuario(String phoneUsuario) { this.phoneUsuario = phoneUsuario; }

    public String getContraseñaUsuario() { return contraseñaUsuario; }
    public void setContraseñaUsuario(String contraseñaUsuario) { this.contraseñaUsuario = contraseñaUsuario; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
