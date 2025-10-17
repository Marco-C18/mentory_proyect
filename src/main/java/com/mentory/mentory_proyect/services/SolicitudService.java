package com.mentory.mentory_proyect.services;

import com.mentory.mentory_proyect.model.*;
import com.mentory.mentory_proyect.repository.SolicitudRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
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

    public void actualizarEstado(Long id, String estado) {
        SolicitudModel solicitud = solicitudRepository.findById(id).orElseThrow();
        solicitud.setEstado(estado);
        solicitudRepository.save(solicitud);
    }
    
    public SolicitudModel obtenerPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }

}
