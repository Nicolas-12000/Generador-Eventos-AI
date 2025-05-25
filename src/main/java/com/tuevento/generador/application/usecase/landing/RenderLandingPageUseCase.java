// src/main/java/com/tuevento/generador/application/usecase/landing/RenderLandingPageUseCase.java
package com.tuevento.generador.application.usecase.landing;

import com.tuevento.generador.application.dto.evento.EventoResponseDTO;
import com.tuevento.generador.application.dto.landing.LandingPageResponseDTO;
import com.tuevento.generador.application.dto.landing.RenderLandingPageViewModel;
import com.tuevento.generador.application.mapper.EventMapper;
import com.tuevento.generador.application.mapper.LandingPageMapper;
import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.port.out.LandingPageRepositoryPort;
import com.tuevento.generador.domain.port.out.EventRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * Use case para preparar todos los datos necesarios para renderizar
 * la landing page (Thymeleaf, Google Maps, secciones dinámicas).
 */
@Service
@RequiredArgsConstructor
public class RenderLandingPageUseCase {

    private final LandingPageRepositoryPort landingRepository;
    private final EventRepositoryPort       eventRepository;

    /** Inyectamos la API key desde application.properties */
    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    /**
     * Obtiene y prepara el ViewModel con:
     * - Datos del evento (DTO)
     * - Datos de la landing page (DTO)
     * - API key de Google Maps
     *
     * @param landingPageId  ID de la landing a renderizar
     * @return ViewModel listo para Thymeleaf
     */
    @Transactional(readOnly = true)
    public RenderLandingPageViewModel execute(Long landingPageId) {
        LandingPage landing = landingRepository.findById(landingPageId)
            .orElseThrow(() -> new NoSuchElementException(
                "Landing page not found: " + landingPageId
            ));

        var event = eventRepository.findById(landing.getEvent().getId())
            .orElseThrow(() -> new NoSuchElementException(
                "Associated event not found: " + landing.getEvent().getId()
            ));

        EventoResponseDTO  eventDto   = EventMapper.toResponseDTO(event);
        LandingPageResponseDTO landingDto = LandingPageMapper.toResponseDTO(landing);

        return RenderLandingPageViewModel.builder()
            .event(eventDto)
            .landing(landingDto)
            .googleMapsApiKey(googleMapsApiKey)
            .build();
    }
}
