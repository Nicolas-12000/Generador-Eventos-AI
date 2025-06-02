package com.tuevento.generador.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class GeminiChatService implements ChatService {

    private final WebClient geminiWebClient;

    @Data
    @AllArgsConstructor
    static class RequestPayload {
        private final String model = "gemini-1.5-pro-latest";
        private final String prompt;
    }

    @Data
    static class Message {
        @JsonProperty("content")
        private String content;
    }

    @Data
    static class Choice {
        @JsonProperty("message")
        private Message message;
    }

    @Data
    static class GeminiResponse {
        @JsonProperty("choices")
        private Choice[] choices;
    }

    @Override
    public String ask(String prompt) {
        // Construyes y envías el payload
        RequestPayload payload = new RequestPayload(prompt);
        GeminiResponse resp = geminiWebClient.post()
            .bodyValue(payload)
            .retrieve()
            .bodyToMono(GeminiResponse.class)
            .block();

        if (resp == null || resp.getChoices().length == 0) {
            throw new RuntimeException("Respuesta vacía de Gemini");
        }

        return resp.getChoices()[0].getMessage().getContent();
    }
}
