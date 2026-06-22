package com.example.emailSender;

import com.example.emailSender.dto.ContactRequest;
import com.example.emailSender.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    private final EmailService emailService;

    public ContactController(
            EmailService emailService
    ) {
        this.emailService = emailService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API Working");
    }

    @PostMapping
    public ResponseEntity<?> sendEmail(
            @Valid @RequestBody ContactRequest request
    ) throws Exception {

        emailService.send(request);

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message",
                        "Email sent successfully"
                )
        );
    }
}