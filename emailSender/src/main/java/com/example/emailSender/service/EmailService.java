package com.example.emailSender.service;

import com.example.emailSender.dto.ContactRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String receiverEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(ContactRequest request) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(receiverEmail);
        message.setSubject("Portfolio Contact: " + request.getSubject());

        message.setText(
                "Name: " + request.getName() + "\n" +
                "Email: " + request.getEmail() + "\n\n" +
                "Message:\n" +
                request.getMessage()
        );

        mailSender.send(message);
    }
}