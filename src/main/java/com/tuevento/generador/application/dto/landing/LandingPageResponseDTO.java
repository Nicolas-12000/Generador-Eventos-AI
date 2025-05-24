package com.tuevento.generador.application.dto.landing;

import lombok.Builder;
import lombok.Data;

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
}