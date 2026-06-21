package com.example.emailSender;

import com.example.emailSender.dto.ContactRequest;
import com.example.emailSender.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private EmailService emailService;

    @GetMapping("/test")
    public String test() {
        return "API Working";
    }

    @PostMapping
    public ResponseEntity<?> sendEmail(
            @Valid @RequestBody ContactRequest request) {

        emailService.send(request);

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Email sent"
                )
        );
    }
}