package com.tuevento.generador.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuevento.generador.application.dto.GenerateEventResponse;
import com.tuevento.generador.application.dto.EventDto;
import com.tuevento.generador.application.mapper.EventMapper;
import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final ChatService chatService;
    private final ObjectMapper objectMapper;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    /**
     * Genera un evento completo usando Gemini, lo persiste y devuelve el DTO.
     *
     * @param userMessage       El mensaje libre del usuario describiendo el evento deseado.
     * @param organizerUsername El username del organizador (extraído del JWT).
     * @param organizerEmail    El email del organizador.
     * @return El EventDto con todos los datos rellenados y persistidos.
     */
    public EventDto generateEvent(String userMessage,
                                  String organizerUsername,
                                  String organizerEmail) throws Exception {
        // 1) Construye el prompt combinando tu plantilla base + mensaje de usuario
        String prompt = buildPrompt(userMessage);

        // 2) Llama al servicio de chat (Gemini)
        String rawJson = chatService.ask(prompt);

        // 3) Parsea la respuesta de Gemini a tu DTO intermedio
        GenerateEventResponse geminiResp = objectMapper.readValue(rawJson, GenerateEventResponse.class);
        EventDto dto = geminiResp.getEventDto();

        // 4) Mapea a entidad JPA
        Event ev = eventMapper.toEntity(dto);

        // 5) Sobrescribe los datos del organizador
        ev.setOrganizerName(organizerUsername);
        ev.setOrganizerEmail(organizerEmail);

        // 6) Persiste en BD (cascada guardará itinerarios, speakers, sponsors)
        Event saved = eventRepository.save(ev);

        // 7) Devuelve el DTO ya persistido (con UUID generado)
        return eventMapper.toDto(saved);
    }

    /**
     * Monta el prompt enviado a Gemini, incorporando tu HTML base como guía Thymeleaf.
     */
    private String buildPrompt(String userMsg) {
        String thymeleafTemplate = """
            Aquí tienes la plantilla HTML de la landing page:
            ```html
            <!DOCTYPE html>
            <html xmlns:th="http://www.thymeleaf.org">
            <head>
              <meta charset="UTF-8">
              <title th:text="${vm.landing.customTitle}">Event Landing</title>
              <!-- CSS principal… -->
            </head>
            <body>
              <header>
                <h1 th:text="${vm.event.name}">Tech Summit 2025</h1>
                <p th:text="${vm.landing.welcomeMessage}">Join us…</p>
              </header>
              <section th:if="${vm.landing.showMap}" id="map-section">…</section>
              <section th:if="${vm.landing.showSchedule}" id="schedule-section">…</section>
              <section th:if="${vm.landing.showSpeakers}" id="speakers-section">…</section>
              <footer>Coste: <span th:text="${vm.event.tokenCost}"/> tokens</footer>
            </body>
            </html>
            ```
            Utiliza esta estructura y llena el JSON con los siguientes campos:
            - event.name, description, eventDateTime (ISO), locationDetails, locationAddress,
              organizerName, organizerEmail, eventType, maxAttendees, ticketUrl, eventImageUrl, tokenCost
            - itineraries: lista de objetos { title, description, startTime, endTime }
            - speakers: lista de objetos { name, biography, photoUrl, email, linkedin, twitter }
            - sponsors: lista de objetos { name, logoUrl, website, sponsorshipLevel }
            """;

        return thymeleafTemplate
             + "\n\nUsuario: " + userMsg;
    }
}
