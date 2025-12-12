package com.mentory.mentory_proyect.Controller;

import com.mentory.mentory_proyect.model.*;
import com.mentory.mentory_proyect.services.*;

import jakarta.servlet.http.HttpSession;

import com.mentory.mentory_proyect.Security.CustomUserDetails;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final SolicitudService solicitudService;
    private final NotificacionService notificacionService;
    private final MentorService mentorService;
    private final AprendizService aprendizService;
    private final RecordatorioService recordatorioService;

    public HomeController(SolicitudService solicitudService,
                          NotificacionService notificacionService,
                          MentorService mentorService,
                          AprendizService aprendizService,
                          RecordatorioService recordatorioService) {
        this.solicitudService = solicitudService;
        this.notificacionService = notificacionService;
        this.mentorService = mentorService;
        this.aprendizService = aprendizService;
        this.recordatorioService = recordatorioService;                        
    }

    private String getEmail(Authentication auth) {
        if (auth == null) return null;
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails custom) {
            return custom.getUsername();
        }
        return auth.getName();
    }

    @GetMapping("/aprendiz-home")
    public String homeAprendiz(Model model, Authentication auth) {
        String email = getEmail(auth);
        AprendizModel aprendiz = aprendizService.buscarPorEmail(email);
        if (aprendiz == null) return "redirect:/login";

        model.addAttribute("aprendiz", aprendiz);
        model.addAttribute("mentores", mentorService.listarTodos());
        model.addAttribute("solicitudes", solicitudService.listarSolicitudesAprendiz(aprendiz));
        model.addAttribute("notificaciones", notificacionService.obtenerNotificaciones(aprendiz.getId()));
        model.addAttribute("notiCount", notificacionService.contarNoLeidas(aprendiz.getId()));
        model.addAttribute("recordatorios", recordatorioService.listarPorUsuario(aprendiz.getId()));
          
        model.addAttribute("misMentores",
        solicitudService.listarSolicitudesAceptadasPorAprendiz(aprendiz));

        return "home-aprendiz";
    }

@GetMapping("/buscarMentores")
public String buscarMentores(
        @RequestParam String especialidad,
        @RequestParam String ciclo,
        Model model,
        Authentication auth) {

    String email = auth.getName();
    AprendizModel aprendiz = aprendizService.buscarPorEmail(email);

    if (aprendiz == null) {
        return "redirect:/login";
    }

    List<MentorModel> mentoresFiltrados = mentorService.buscarPorFiltros(especialidad, ciclo);

    model.addAttribute("aprendiz", aprendiz);
    model.addAttribute("mentores", mentoresFiltrados);
    model.addAttribute("solicitudes", solicitudService.listarSolicitudesAprendiz(aprendiz));
    model.addAttribute("notificaciones", notificacionService.obtenerNotificaciones(aprendiz.getId()));
    model.addAttribute("notiCount", notificacionService.contarNoLeidas(aprendiz.getId()));

    return "home-aprendiz";
}

    @PostMapping("/recordatorios/guardar")
    public String guardarRecordatorio(
            @RequestParam Long usuarioId,
            @RequestParam String mensaje,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha) {

        recordatorioService.crearRecordatorio(usuarioId, mensaje, fecha);
        return "redirect:/aprendiz-home";
    }



  @GetMapping("/mentor-home")
public String homeMentor(Model model, Authentication auth, HttpSession session) {

    String email = getEmail(auth);
    MentorModel mentor = mentorService.buscarPorEmail(email);

    if (mentor == null) return "redirect:/login";

    session.setAttribute("usuarioId", mentor.getId());

    model.addAttribute("mentor", mentor);

    model.addAttribute("solicitudes",
            solicitudService.listarSolicitudesMentor(mentor));

    model.addAttribute("solicitudesAceptadas",
            solicitudService.listarSolicitudesAceptadas(mentor));

    model.addAttribute("notificaciones",
            notificacionService.obtenerNotificaciones(mentor.getId()));

    model.addAttribute("notiCount",
            notificacionService.contarNoLeidas(mentor.getId()));

    // ✅ NUEVAS ESTADÍSTICAS (SEGURO)
    model.addAttribute("totalSolicitudes",
            solicitudService.contarSolicitudesPorMentor(mentor.getId()));

    model.addAttribute("totalAceptadas",
            solicitudService.contarSolicitudesAceptadas(mentor.getId()));

    model.addAttribute("promedioCalificacion",
            solicitudService.obtenerPromedioCalificacion(mentor.getId()));

    return "home-mentor";
}



}
