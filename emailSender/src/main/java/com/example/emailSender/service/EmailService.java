package com.example.emailSender.service;

import com.example.emailSender.dto.ContactRequest;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class EmailService {

    @Value("${resend.api-key}")
    private String apiKey;

  public void send(ContactRequest request) throws Exception {

    OkHttpClient client = new OkHttpClient();

    String html = """
        <h2>New Portfolio Contact</h2>

        <p><b>Name:</b> %s</p>
        <p><b>Email:</b> %s</p>
        <p><b>Subject:</b> %s</p>

        <p><b>Message:</b></p>
        <p>%s</p>
        """
            .formatted(
                    request.getName(),
                    request.getEmail(),
                    request.getSubject(),
                    request.getMessage()
            );

    Map<String, Object> payload = new HashMap<>();

    payload.put(
            "from",
            "onboarding@resend.dev"
    );

    payload.put(
            "to",
            List.of("saravanandurai938@gmail.com")
    );

    payload.put(
            "subject",
            "Portfolio Contact Form"
    );

    payload.put(
            "html",
            html
    );

    ObjectMapper mapper =
            new ObjectMapper();

    String json =
            mapper.writeValueAsString(payload);

    RequestBody body =
            RequestBody.create(
                    json,
                    MediaType.parse(
                            "application/json"
                    )
            );

    Request resendRequest =
            new Request.Builder()
                    .url(
                            "https://api.resend.com/emails"
                    )
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
                 client.newCall(resendRequest)
                         .execute()) {

        String responseBody =
                response.body() != null
                        ? response.body().string()
                        : "";

        System.out.println(
                "RESEND RESPONSE: "
                        + responseBody
        );

        if (!response.isSuccessful()) {
            throw new RuntimeException(
                    "Resend Error: "
                            + response.code()
                            + " "
                            + responseBody
            );
        }
    }
}
}