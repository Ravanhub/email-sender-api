package com.example.emailSender.service;

import com.example.emailSender.dto.ContactRequest;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${resend.api-key}")
    private String apiKey;

    public void send(ContactRequest request) throws IOException {

        OkHttpClient client = new OkHttpClient();

        String html = """
                <h2>New Portfolio Contact</h2>

                <p><strong>Name:</strong> %s</p>

                <p><strong>Email:</strong> %s</p>

                <p><strong>Subject:</strong> %s</p>

                <p><strong>Message:</strong></p>

                <p>%s</p>
                """
                .formatted(
                        request.getName(),
                        request.getEmail(),
                        request.getSubject(),
                        request.getMessage()
                );

        String json = """
                {
                  "from":"Portfolio Contact <onboarding@resend.dev>",
                  "to":["saravanandurai938@gmail.com"],
                  "subject":"Portfolio Contact Form",
                  "html":%s
                }
                """
                .formatted("\"" + html.replace("\"", "\\\"") + "\"");

        RequestBody body =
                RequestBody.create(
                        json,
                        MediaType.parse("application/json")
                );

        Request resendRequest =
                new Request.Builder()
                        .url("https://api.resend.com/emails")
                        .post(body)
                        .addHeader(
                                "Authorization",
                                "Bearer " + apiKey
                        )
                        .addHeader(
                                "Content-Type",
                                "application/json"
                        )
                        .build();

        try (Response response =
                     client.newCall(resendRequest).execute()) {

            if (!response.isSuccessful()) {
                throw new RuntimeException(
                        "Resend Error: "
                                + response.code()
                                + " "
                                + response.body().string()
                );
            }
        }
    }
}