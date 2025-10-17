package com.mentory.mentory_proyect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class NotificacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private boolean leida = false;

    private LocalDateTime fecha = LocalDateTime.now();

    // El destinatario puede ser un aprendiz o un mentor (por ID)
    private Long destinatarioId;

    public NotificacionModel() {}

    public NotificacionModel(String mensaje, Long destinatarioId) {
        this.mensaje = mensaje;
        this.destinatarioId = destinatarioId;
        this.fecha = LocalDateTime.now();
        this.leida = false;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }
}
