package com.mentory.mentory_proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mentores")
public class MentorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreUsuario;

    private String phoneUsuario;

    private String emailUsuario;

    private String contraseñaUsuario;

    private String cicloAcademico;

    private String especialidad;

    public MentorModel() {
    }

    public MentorModel(String nombreUsuario, String phoneUsuario, String emailUsuario, String contraseñaUsuario,
            String cicloAcademico, String especialidad) {
        this.nombreUsuario = nombreUsuario;
        this.phoneUsuario = phoneUsuario;
        this.emailUsuario = emailUsuario;
        this.contraseñaUsuario = contraseñaUsuario;
        this.cicloAcademico = cicloAcademico;
        this.especialidad = especialidad;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPhoneUsuario() {
        return phoneUsuario;
    }

    public void setPhoneUsuario(String phoneUsuario) {
        this.phoneUsuario = phoneUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getContraseñaUsuario() {
        return contraseñaUsuario;
    }

    public void setContraseñaUsuario(String contraseñaUsuario) {
        this.contraseñaUsuario = contraseñaUsuario;
    }

    public String getCicloAcademico() {
        return cicloAcademico;
    }

    public void setCicloAcademico(String cicloAcademico) {
        this.cicloAcademico = cicloAcademico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

}
