package com.example.emailSender.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactRequest(

        @NotBlank(message = "Name required")
        String name,

        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Subject required")
        String subject,

        @NotBlank(message = "Message required")
        String message

) {}
