package com.mentory.mentory_proyect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class UsuarioBaseModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombreUsuario;
    
    @Column(nullable = false, unique = true)
    private String emailUsuario;
        
    @Column(nullable = false)
    private String contraseñaUsuario;
    
    @Column(nullable = false)
    private String rol = "PENDIENTE"; // Por defecto
    
    @Column(nullable = false)
    private Boolean perfilCompletado = false;
    
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    // Constructor vacío
    public UsuarioBaseModel() {}
    
    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
    
    public String getContraseñaUsuario() { return contraseñaUsuario; }
    public void setContraseñaUsuario(String contraseñaUsuario) { this.contraseñaUsuario = contraseñaUsuario; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public Boolean getPerfilCompletado() { return perfilCompletado; }
    public void setPerfilCompletado(Boolean perfilCompletado) { this.perfilCompletado = perfilCompletado; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}