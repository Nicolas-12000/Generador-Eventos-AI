package com.tuevento.generador.application.dto.landing;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * DTO to create a new landing page associated with an event.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandingPageCreateDTO {
    private Long eventId;
    private String customTitle;
    private String welcomeMessage;
    private String backgroundImageUrl;
    private String accentColor;
    private boolean showMap;
    private boolean showSchedule;
    private boolean showSpeakers;
    private List<SpeakerInfoDTO> speakers;

        // Dirección del evento, para geocodificación o despliegue en mapa
    private String locationAddress;
    private Double latitude;
    private Double longitude;

    // Secciones adicionales
    private List<ScheduleItemDTO> schedule;
}