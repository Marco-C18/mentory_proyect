package com.mentory.mentory_proyect.Controller;

import com.mentory.mentory_proyect.model.*;
import com.mentory.mentory_proyect.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;
    private final MentorService mentorService;
    private final AprendizService aprendizService;
    private final NotificacionService notificacionService;

    public SolicitudController(SolicitudService solicitudService, 
                               MentorService mentorService, 
                               AprendizService aprendizService, 
                               NotificacionService notificacionService) {
        this.solicitudService = solicitudService;
        this.mentorService = mentorService;
        this.aprendizService = aprendizService;
        this.notificacionService = notificacionService;
    }

    // Enviar solicitud desde el aprendiz al mentor
    @PostMapping("/enviar/{mentorId}")
    public String enviarSolicitud(@PathVariable Long mentorId, @RequestParam Long aprendizId) {
        MentorModel mentor = mentorService.obtenerPorId(mentorId);
        AprendizModel aprendiz = aprendizService.obtenerPorId(aprendizId);
        solicitudService.enviarSolicitud(aprendiz, mentor);

        // Notificación para el mentor
        String mensaje = "El aprendiz " + aprendiz.getNombreUsuario() + " te ha enviado una solicitud de mentoría.";
        NotificacionModel noti = new NotificacionModel(mensaje, mentor.getId());
        notificacionService.guardarNotificacion(noti);

        return "redirect:/aprendiz-home";
    }

    // Responder solicitud (aceptar o rechazar)
    @PostMapping("/responder/{id}")
    public String responderSolicitud(@PathVariable Long id, @RequestParam String estado) {
        solicitudService.actualizarEstado(id, estado);

        // Obtener solicitud actualizada para enviar la notificación al aprendiz
        SolicitudModel solicitud = solicitudService.obtenerPorId(id);
        String mensaje;
        if (estado.equalsIgnoreCase("aceptada")) {
            mensaje = "Tu solicitud fue ACEPTADA por el mentor " + solicitud.getMentor().getNombreUsuario();
        } else {
            mensaje = "Tu solicitud fue RECHAZADA por el mentor " + solicitud.getMentor().getNombreUsuario();
        }

        // Crear notificación para el aprendiz
        NotificacionModel noti = new NotificacionModel(mensaje, solicitud.getAprendiz().getId());
        notificacionService.guardarNotificacion(noti);

        return "redirect:/mentor-home";
    }

    @PostMapping("/mentor/calificar")
    public String calificarAprendiz(
        @RequestParam Long solicitudId,
        @RequestParam Integer calificacion,
        @RequestParam String retroalimentacion) {

    SolicitudModel solicitud = solicitudService.obtenerPorId(solicitudId);
    solicitud.setCalificacion(calificacion);
    solicitud.setRetroalimentacion(retroalimentacion);
    solicitudService.guardar(solicitud);

    return "redirect:/mentor-home";

}


}
