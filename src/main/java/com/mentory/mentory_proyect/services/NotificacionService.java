package com.mentory.mentory_proyect.services;

import com.mentory.mentory_proyect.model.NotificacionModel;
import com.mentory.mentory_proyect.repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionService {

    private final NotificacionRepository repo;

    public NotificacionService(NotificacionRepository repo) {
        this.repo = repo;
    }

    public List<NotificacionModel> obtenerNotificaciones(Long destinatarioId) {
        return repo.findByDestinatarioIdOrderByFechaDesc(destinatarioId);
    }

    public long contarNoLeidas(Long destinatarioId) {
        return repo.countByDestinatarioIdAndLeidaFalse(destinatarioId);
    }

    public void marcarLeida(Long id) {
        NotificacionModel n = repo.findById(id).orElseThrow();
        n.setLeida(true);
        repo.save(n);
    }

    public void crearNotificacion(String mensaje, Long destinatarioId) {
        NotificacionModel notificacion = new NotificacionModel(mensaje, destinatarioId);
        repo.save(notificacion);
    }

    public void guardarNotificacion(NotificacionModel noti) {
    repo.save(noti);
}

}
