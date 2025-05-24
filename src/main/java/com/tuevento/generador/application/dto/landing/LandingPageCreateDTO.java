package com.tuevento.generador.application.dto.landing;

import lombok.Data;

@Data
public class LandingPageCreateDTO {
    private Long eventId; // ID del evento al que pertenece la landing
    private String customTitle;
    private String welcomeMessage;
    private String backgroundImageUrl;
    private String accentColor;
    private boolean showMap;
    private boolean showAgenda;
    private boolean showSpeakers;
}