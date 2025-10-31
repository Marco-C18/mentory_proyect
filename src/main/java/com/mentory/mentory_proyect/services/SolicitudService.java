package com.mentory.mentory_proyect.services;

import com.mentory.mentory_proyect.model.*;
import com.mentory.mentory_proyect.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final EmailService emailService;

    // EmailService 
    public SolicitudService(SolicitudRepository solicitudRepository, EmailService emailService) {
        this.solicitudRepository = solicitudRepository;
        this.emailService = emailService;
    }

    public SolicitudModel enviarSolicitud(AprendizModel aprendiz, MentorModel mentor) {
        SolicitudModel solicitud = new SolicitudModel();
        solicitud.setAprendiz(aprendiz);
        solicitud.setMentor(mentor);
        solicitud.setEstado("pendiente");
        solicitud.setMensaje("Nueva solicitud de mentoría");

        return solicitudRepository.save(solicitud);
    }

    public List<SolicitudModel> listarSolicitudesMentor(MentorModel mentor) {
        return solicitudRepository.findByMentor(mentor);
    }

    public List<SolicitudModel> listarSolicitudesAprendiz(AprendizModel aprendiz) {
        return solicitudRepository.findByAprendiz(aprendiz);
    }

    // Envío del correo cuando se acepta / rechaza la solicitud
    public void actualizarEstado(Long id, String estado) {
        SolicitudModel solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(estado);
        solicitudRepository.save(solicitud);

        String email = solicitud.getAprendiz().getEmailUsuario();
        String asunto = "Actualización de tu solicitud de mentoría";
        String mensaje = "Hola " + solicitud.getAprendiz().getNombreUsuario()
                + ", tu solicitud fue: " + estado;

        emailService.enviarEmail(email, asunto, mensaje);
        System.out.println("📧 Email enviado a: " + email);
    }

    public SolicitudModel obtenerPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }
}
