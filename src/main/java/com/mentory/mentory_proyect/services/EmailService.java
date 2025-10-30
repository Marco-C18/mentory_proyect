package com.mentory.mentory_proyect.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(String para, String asunto, String mensaje) {

        System.out.println("----------------------------------------------------------");
        System.out.println("📨 Enviando correo...");
        System.out.println("➡ Para: " + para);
        System.out.println("➡ Asunto: " + asunto);
        System.out.println("----------------------------------------------------------");

        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(para);
            email.setSubject(asunto);
            email.setText(mensaje);

            mailSender.send(email);

            System.out.println("✅ CORREO ENVIADO EXITOSAMENTE ✅");
            System.out.println("----------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("❌ ERROR AL ENVIAR EL CORREO ❌");
            System.out.println("➡ Razón: " + e.getMessage());
            System.out.println("----------------------------------------------------------");
        }
    }
}
