package com.tuevento.generador.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuevento.generador.application.dto.EventDto;
import com.tuevento.generador.application.mapper.EventMapper;
import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final ResourceLoader resourceLoader;
    private final ChatService chatService;  // Aquí se llama al Gemini
    private final ObjectMapper objectMapper;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    /**
     * Genera un evento con base en la interacción con Gemini, usando un template Thymeleaf
     * y un contexto semántico que incluye datos del organizador y eventos relacionados.
     */
    public EventDto generateEvent(
        String userMessage,
        String templateName,
        String organizerName,
        String organizerEmail
    ) throws Exception {

        // 1) Carga el template Thymeleaf que funciona como guía para la petición (prompt)
        String thymeleafTemplate = loadTemplate(templateName);

        // 2) Construye el contexto semántico que se inyectará en el prompt
        Map<String, Object> semanticContext = buildSemanticContext(organizerName, organizerEmail);

        // 3) Convierte el contexto a JSON para pasar como contexto dentro del prompt
        String semanticJson = objectMapper.writeValueAsString(semanticContext);

        // 4) Construye el prompt completo para el modelo Gemini
        String prompt = thymeleafTemplate
            + "\n\nContexto del evento:\n" + semanticJson
            + "\n\nUsuario: " + userMessage;

        // 5) Llama a Gemini a través del servicio chatService
        String chatResponse = chatService.ask(prompt);

        // 6) Parsear la respuesta JSON que genera Gemini a nuestro DTO
        EventDto eventDto = objectMapper.readValue(chatResponse, EventDto.class);

        // 7) Añadir datos del organizador al DTO
        eventDto.setOrganizerName(organizerName);
        eventDto.setOrganizerEmail(organizerEmail);

        // 8) Guardar en base de datos el evento mapeado desde DTO
        Event eventEntity = eventMapper.toEntity(eventDto);
        eventEntity.setOrganizerName(organizerName);
        eventEntity.setOrganizerEmail(organizerEmail);
        eventEntity = eventRepository.save(eventEntity);

        // 9) Retornar DTO actualizado
        return eventMapper.toDto(eventEntity);
    }

    /**
     * Carga un template Thymeleaf desde recursos para usar en prompt.
     */
    private String loadTemplate(String name) throws IOException {
        Resource res = resourceLoader.getResource("classpath:templates/prompts/" + name + ".html");
        if (!res.exists()) {
            throw new IllegalArgumentException("Plantilla no encontrada: " + name);
        }
        try (InputStream in = res.getInputStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    /**
     * Construye el contexto con información del organizador y sus eventos,
     * para ser usado por Gemini en generación contextualizada.
     */
    private Map<String, Object> buildSemanticContext(String organizerUsername, String organizerEmail) {
        Map<String, Object> context = new HashMap<>();

        // Datos organizador
        Map<String, String> organizer = new HashMap<>();
        organizer.put("username", organizerUsername);
        organizer.put("email", organizerEmail);
        context.put("organizer", organizer);

        // Eventos del organizador
        List<Event> events = eventRepository.findByOrganizerName(organizerUsername);
        List<Map<String, Object>> eventList = new ArrayList<>();

        for (Event e : events) {
            Map<String, Object> ev = new HashMap<>();
            ev.put("name", e.getName());
            ev.put("description", e.getDescription());
            ev.put("dateTime", e.getEventDateTime());
            ev.put("location", e.getLocationAddress());
            ev.put("maxAttendees", e.getMaxAttendees());

            // Speakers
            List<Map<String, String>> speakers = e.getSpeakers().stream()
                .map(s -> {
                    Map<String, String> m = new HashMap<>();
                    m.put("name", s.getName());
                    m.put("bio", s.getBiography());
                    return m;
                })
                .collect(Collectors.toList());
            ev.put("speakers", speakers);

            // Sponsors
            List<Map<String, String>> sponsors = e.getSponsors().stream()
                .map(sp -> {
                    Map<String, String> m = new HashMap<>();
                    m.put("name", sp.getName());
                    m.put("website", sp.getWebsite());
                    return m;
                })
                .collect(Collectors.toList());
            ev.put("sponsors", sponsors);

            // Itinerarios
            List<Map<String, Object>> itineraries = e.getItineraries().stream()
                .map(it -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("start", it.getStartTime());
                    m.put("end", it.getEndTime());
                    m.put("topic", it.getDescription());
                    return m;
                })
                .collect(Collectors.toList());
            ev.put("itineraries", itineraries);

            eventList.add(ev);
        }

        context.put("events", eventList);
        return context;
    }
}
