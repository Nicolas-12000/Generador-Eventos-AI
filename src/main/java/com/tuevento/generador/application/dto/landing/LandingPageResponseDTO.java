package com.tuevento.generador.application.dto.landing;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class LandingPageResponseDTO {
    private Long id;
    private Long eventId;
    private String customTitle;
    private String welcomeMessage;
    private String backgroundImageUrl;
    private String accentColor;
    private boolean showMap;
    private boolean showSchedule;
    private boolean showSpeakers;

    // Dirección del evento, para geocodificación o despliegue en mapa
    private String locationAddress;
    private Double latitude;
    private Double longitude;

    // Secciones adicionales
    private List<SpeakerInfoDTO> speakers;
    private List<ScheduleItemDTO> schedule;
}