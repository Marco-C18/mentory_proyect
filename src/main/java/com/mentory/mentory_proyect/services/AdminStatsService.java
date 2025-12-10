package com.mentory.mentory_proyect.services;

import com.mentory.mentory_proyect.repository.*;
import org.springframework.stereotype.Service;

@Service
public class AdminStatsService {

    private final MentorRepository mentorRepository;
    private final AprendizRepository aprendizRepository;
    private final SolicitudRepository solicitudRepository;
    private final RecordatorioRepository recordatorioRepository;

    public AdminStatsService(MentorRepository mentorRepository,
                            AprendizRepository aprendizRepository,
                            SolicitudRepository solicitudRepository,
                            RecordatorioRepository recordatorioRepository) {
        this.mentorRepository = mentorRepository;
        this.aprendizRepository = aprendizRepository;
        this.solicitudRepository = solicitudRepository;
        this.recordatorioRepository = recordatorioRepository;
    }

    public long getTotalEstudiantes() {
        return aprendizRepository.count();
    }

    public long getTotalMentores() {
        return mentorRepository.count();
    }

    public long getTotalSolicitudes() {
        return solicitudRepository.count();
    }

    public long getTotalRecordatorios() {
        return recordatorioRepository.count();
    }

    public long getSolicitudesPendientes() {
        return solicitudRepository.countByEstado("pendiente");
    }

    public long getSolicitudesAceptadas() {
        return solicitudRepository.countByEstado("aceptada");
    }

    public long getSolicitudesRechazadas() {
        return solicitudRepository.countByEstado("rechazada");
    }
}