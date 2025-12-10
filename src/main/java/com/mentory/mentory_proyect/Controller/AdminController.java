package com.mentory.mentory_proyect.Controller;

import com.mentory.mentory_proyect.services.AdminStatsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminStatsService adminStatsService;
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMIN_SESSION_KEY = "admin_authenticated";

    public AdminController(AdminStatsService adminStatsService) {
        this.adminStatsService = adminStatsService;
    }

    @GetMapping
    public String adminPage(HttpSession session, Model model) {
        Boolean isAuthenticated = (Boolean) session.getAttribute(ADMIN_SESSION_KEY);
        
        if (isAuthenticated != null && isAuthenticated) {
            // Cargar estadísticas
            model.addAttribute("totalEstudiantes", adminStatsService.getTotalEstudiantes());
            model.addAttribute("totalMentores", adminStatsService.getTotalMentores());
            model.addAttribute("totalSolicitudes", adminStatsService.getTotalSolicitudes());
            model.addAttribute("totalRecordatorios", adminStatsService.getTotalRecordatorios());
            model.addAttribute("solicitudesPendientes", adminStatsService.getSolicitudesPendientes());
            model.addAttribute("solicitudesAceptadas", adminStatsService.getSolicitudesAceptadas());
            model.addAttribute("solicitudesRechazadas", adminStatsService.getSolicitudesRechazadas());
            
            return "admin/dashboard";
        }
        
        return "admin/login_admin";
    }

    @PostMapping("/login")
    public String adminLogin(@RequestParam("password") String password, 
                            HttpSession session, 
                            Model model) {
        if (ADMIN_PASSWORD.equals(password)) {
            session.setAttribute(ADMIN_SESSION_KEY, true);
            return "redirect:/admin";
        }
        
        model.addAttribute("error", "Contraseña incorrecta");
        return "admin/login_admin";
    }

    @GetMapping("/logout")
    public String adminLogout(HttpSession session) {
        session.removeAttribute(ADMIN_SESSION_KEY);
        return "redirect:/admin";
    }
}