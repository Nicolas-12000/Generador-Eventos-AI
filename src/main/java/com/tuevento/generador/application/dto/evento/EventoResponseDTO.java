package com.tuevento.generador.application.dto.evento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tuevento.generador.domain.model.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de respuesta para operaciones con eventos
 * Incluye factory method para conversión desde entidad
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime eventDateTime;
    
    private String locationDetails;
    private String locationAddress;
    private String aiGeneratedDescription;
    private String aiGeneratedKeywords;
    private String status;
    private UUID userId;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    
    // Detalles adicionales del organizador
    private String organizerName;
    private String organizerEmail;
    private String organizerPhone;
    private String eventType;
    private Integer maxAttendees;
    private String ticketUrl;
    private String eventImageUrl;
    
    // Información de estado del evento
    private boolean readyForPublication;
    private boolean hasLandingPage;
    
    /**
     * Factory method para crear DTO desde entidad Event
     */
    public static EventoResponseDTO fromEntity(Event event) {
        return EventoResponseDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .eventDateTime(event.getEventDateTime())
                .locationDetails(event.getLocationDetails())
                .locationAddress(event.getLocationAddress())
                .aiGeneratedDescription(event.getAiGeneratedDescription())
                .aiGeneratedKeywords(event.getAiGeneratedKeywords())
                .status(event.getStatus().name())
                .userId(event.getUserId())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .organizerName(event.getOrganizerName())
                .organizerEmail(event.getOrganizerEmail())
                .organizerPhone(event.getOrganizerPhone())
                .eventType(event.getEventType())
                .maxAttendees(event.getMaxAttendees())
                .ticketUrl(event.getTicketUrl())
                .eventImageUrl(event.getEventImageUrl())
                .readyForPublication(event.isReadyForPublication())
                .hasLandingPage(event.getLandingPage() != null)
                .build();
    }
    
    /**
     * Factory method para crear DTO resumido (para listas)
     */
    public static EventoResponseDTO fromEntitySummary(Event event) {
        return EventoResponseDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .eventDateTime(event.getEventDateTime())
                .locationDetails(event.getLocationDetails())
                .status(event.getStatus().name())
                .eventType(event.getEventType())
                .createdAt(event.getCreatedAt())
                .readyForPublication(event.isReadyForPublication())
                .hasLandingPage(event.getLandingPage() != null)
                .build();
    }
}
