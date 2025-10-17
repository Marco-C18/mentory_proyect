package com.mentory.mentory_proyect.repository;

import com.mentory.mentory_proyect.model.NotificacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<NotificacionModel, Long> {
    List<NotificacionModel> findByDestinatarioIdOrderByFechaDesc(Long destinatarioId);
    long countByDestinatarioIdAndLeidaFalse(Long destinatarioId);
}
