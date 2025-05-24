package com.tuevento.generador.application.mapper;

import java.time.LocalDateTime;

import com.tuevento.generador.application.dto.evento.EventoCreateDTO;
import com.tuevento.generador.application.dto.evento.EventoResponseDTO;
import com.tuevento.generador.application.dto.evento.EventoUpdateDTO;
import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.domain.model.User;

public class EventMapper {

    public static Event toEntity(EventoCreateDTO dto, User user) {
        return Event.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .eventDateTime(dto.getEventDateTime())
                .locationDetails(dto.getLocationDetails())
                .locationAddress(dto.getLocationAddress())
                .organizerName(dto.getOrganizerName())
                .organizerEmail(dto.getOrganizerEmail())
                .organizerPhone(dto.getOrganizerPhone())
                .eventType(dto.getEventType())
                .maxAttendees(dto.getMaxAttendees())
                .ticketUrl(dto.getTicketUrl())
                .eventImageUrl(dto.getEventImageUrl())
                .user(user)
                .status(Event.EventStatus.DRAFT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static void updateEntityFromDTO(Event event, EventoUpdateDTO dto) {
        dto.getName().ifPresent(event::setName);
        dto.getDescription().ifPresent(event::setDescription);
        dto.getEventDateTime().ifPresent(event::setEventDateTime);
        dto.getLocationDetails().ifPresent(event::setLocationDetails);
        dto.getLocationAddress().ifPresent(event::setLocationAddress);
        dto.getOrganizerName().ifPresent(event::setOrganizerName);
        dto.getOrganizerEmail().ifPresent(event::setOrganizerEmail);
        dto.getOrganizerPhone().ifPresent(event::setOrganizerPhone);
        dto.getEventType().ifPresent(event::setEventType);
        dto.getMaxAttendees().ifPresent(event::setMaxAttendees);
        dto.getTicketUrl().ifPresent(event::setTicketUrl);
        dto.getEventImageUrl().ifPresent(event::setEventImageUrl);
        event.setUpdatedAt(LocalDateTime.now());
    }

    public static EventoResponseDTO toResponseDTO(Event event) {
        return EventoResponseDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .eventDateTime(event.getEventDateTime())
                .locationDetails(event.getLocationDetails())
                .locationAddress(event.getLocationAddress())
                .organizerName(event.getOrganizerName())
                .organizerEmail(event.getOrganizerEmail())
                .organizerPhone(event.getOrganizerPhone())
                .eventType(event.getEventType())
                .maxAttendees(event.getMaxAttendees())
                .ticketUrl(event.getTicketUrl())
                .eventImageUrl(event.getEventImageUrl())
                .status(event.getStatus().name())
                .aiGeneratedDescription(event.getAiGeneratedDescription())
                .aiGeneratedKeywords(event.getAiGeneratedKeywords())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .organizerName(event.getUser() != null ? event.getUser().getUsername() : null)
                .organizerEmail(event.getUser() != null ? event.getUser().getEmail()    : null)
                .build();
    }
}
