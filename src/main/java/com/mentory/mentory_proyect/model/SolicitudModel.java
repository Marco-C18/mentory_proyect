package com.mentory.mentory_proyect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
public class SolicitudModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el aprendiz
    @ManyToOne
    @JoinColumn(name = "id_aprendiz", nullable = false)
    private AprendizModel aprendiz;

    // Relación con el mentor
    @ManyToOne
    @JoinColumn(name = "id_mentor", nullable = false)
    private MentorModel mentor;

    private String mensaje;
    private String estado; // pendiente, aceptada, rechazada
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private Integer calificacion;   // 1 a 5 estrellas
    
    @Column(length = 500)
    private String retroalimentacion;

    public Integer getCalificacion() {
    return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public SolicitudModel() {}

    public SolicitudModel(AprendizModel aprendiz, MentorModel mentor, String mensaje, String estado) {
        this.aprendiz = aprendiz;
        this.mentor = mentor;
        this.mensaje = mensaje;
        this.estado = estado;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public AprendizModel getAprendiz() {
        return aprendiz;
    }

    public void setAprendiz(AprendizModel aprendiz) {
        this.aprendiz = aprendiz;
    }

    public MentorModel getMentor() {
        return mentor;
    }

    public void setMentor(MentorModel mentor) {
        this.mentor = mentor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
