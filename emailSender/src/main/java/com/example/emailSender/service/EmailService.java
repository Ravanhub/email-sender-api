package com.example.emailSender.service;

import com.example.emailSender.dto.ContactRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;

    public void send(ContactRequest request) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo("saravanandurai938@gmail.com");

        mail.setSubject(
                "Portfolio Contact : "
                        + request.subject()
        );

        mail.setText(
                "Name : " + request.name()
                        + "\nEmail : " + request.email()
                        + "\n\nMessage:\n"
                        + request.message()
        );

        mailSender.send(mail);
    }
}