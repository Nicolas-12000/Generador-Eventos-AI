package com.tuevento.generador.controller;

import com.tuevento.generador.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * Endpoint para enviar un mensaje al chatbot Gemini y recibir respuesta.
     * @param userMessage mensaje enviado por el usuario.
     * @return respuesta generada por el chatbot.
     */
    @PostMapping("/ask")
    public ResponseEntity<String> askChatbot(@RequestParam String prompt) {
        try {
            String response = chatService.ask(prompt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al procesar la consulta del chatbot");
        }
    }
}
