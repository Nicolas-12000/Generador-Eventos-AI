package com.tuevento.generador.application.dto.landing;

import java.util.Optional;

import lombok.Data;

@Data
public class LandingPageUpdateDTO {
    private Optional<String> customTitle = Optional.empty();
    private Optional<String> welcomeMessage = Optional.empty();
    private Optional<String> backgroundImageUrl = Optional.empty();
    private Optional<String> accentColor = Optional.empty();
    private Optional<Boolean> showMap = Optional.empty();
    private Optional<Boolean> showAgenda = Optional.empty();
    private Optional<Boolean> showSpeakers = Optional.empty();
}
