package com.mentory.mentory_proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class User{

    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreUsuario;

    @Column(nullable = false, unique = true)
    private String emailUsuario;

    @Column(nullable = false, unique = true)
    private String phoneUsuario;

    @Column(nullable = false)
    private String contraseñaUsuario;

    public User() {}

    public User(String nombreUsuario, String emailUsuario, String phoneUsuario, String contraseñaUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.phoneUsuario = phoneUsuario;
        this.contraseñaUsuario = contraseñaUsuario;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
    public String getPhoneUsuario() { return phoneUsuario; }
    public void setPhoneUsuario(String phoneUsuario) { this.phoneUsuario = phoneUsuario; }
    public String getContraseñaUsuario() { return contraseñaUsuario; }
    public void setContraseñaUsuario(String contraseñaUsuario) { this.contraseñaUsuario = contraseñaUsuario; }
}
