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
    private String nombre;
    private String descripcion;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHora;
    
    private String detallesUbicacion;
    private String direccion;
    private String descripcionGeneradaIA;
    private String palabrasClaveGeneradasIA;
    private String estado;
    private UUID usuarioId;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaCreacion;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaActualizacion;
    
    // Detalles adicionales del organizador
    private String nombreOrganizador;
    private String emailOrganizador;
    private String telefonoOrganizador;
    private String tipoEvento;
    private Integer maxAsistentes;
    private String urlTickets;
    private String urlImagen;
    
    // Información de estado del evento
    private boolean listoParaPublicacion;
    private boolean tieneLandingPage;
    
    /**
     * Factory method para crear DTO desde entidad Event
     */
    public static EventoResponseDTO fromEntity(Event event) {
        return EventoResponseDTO.builder()
                .id(event.getId())
                .nombre(event.getName())
                .descripcion(event.getDescription())
                .fechaHora(event.getEventDateTime())
                .detallesUbicacion(event.getLocationDetails())
                .direccion(event.getLocationAddress())
                .descripcionGeneradaIA(event.getAiGeneratedDescription())
                .palabrasClaveGeneradasIA(event.getAiGeneratedKeywords())
                .estado(event.getStatus().name())
                .usuarioId(event.getUserId())
                .fechaCreacion(event.getCreatedAt())
                .fechaActualizacion(event.getUpdatedAt())
                .nombreOrganizador(event.getOrganizerName())
                .emailOrganizador(event.getOrganizerEmail())
                .telefonoOrganizador(event.getOrganizerPhone())
                .tipoEvento(event.getEventType())
                .maxAsistentes(event.getMaxAttendees())
                .urlTickets(event.getTicketUrl())
                .urlImagen(event.getEventImageUrl())
                .listoParaPublicacion(event.isReadyForPublication())
                .tieneLandingPage(event.getLandingPage() != null)
                .build();
    }
    
    /**
     * Factory method para crear DTO resumido (para listas)
     */
    public static EventoResponseDTO fromEntitySummary(Event event) {
        return EventoResponseDTO.builder()
                .id(event.getId())
                .nombre(event.getName())
                .fechaHora(event.getEventDateTime())
                .detallesUbicacion(event.getLocationDetails())
                .estado(event.getStatus().name())
                .tipoEvento(event.getEventType())
                .fechaCreacion(event.getCreatedAt())
                .listoParaPublicacion(event.isReadyForPublication())
                .tieneLandingPage(event.getLandingPage() != null)
                .build();
    }
}