package com.tuevento.generador.application.dto.landing;
import com.tuevento.generador.application.dto.evento.EventoResponseDTO;

import lombok.Builder;
import lombok.Data;

/**
 * ViewModel para el template Thymeleaf de la landing page.
 * Contiene DTOs con todos los datos dinámicos y la API key de Google Maps.
 */
@Data
@Builder
public class RenderLandingPageViewModel {
    private EventoResponseDTO     event;
    private LandingPageResponseDTO landing;
    private String                googleMapsApiKey;
}