package com.tuevento.generador.application.usecase.landing;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuevento.generador.application.dto.landing.LandingPageResponseDTO;
import com.tuevento.generador.application.mapper.LandingPageMapper;
import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.port.out.LandingPageRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetLandingPageUseCase {

    private final LandingPageRepositoryPort repository;

    /**
     * Retrieves a LandingPage by its ID, ensuring the current user owns the underlying event.
     *
     * @param landingPageId  the ID of the landing page to fetch
     * @param currentUserId  the ID of the authenticated user
     * @return               the DTO representing the landing page
     */
    @Transactional(readOnly = true)
    public LandingPageResponseDTO execute(Long landingPageId, UUID currentUserId) {
        LandingPage landing = repository.findById(landingPageId)
            .orElseThrow(() -> new NoSuchElementException(
                "Landing page not found: " + landingPageId
            ));

        Event event = landing.getEvent();
        if (event == null || !event.getUserId().equals(currentUserId)) {
            throw new SecurityException("Not authorized to view this landing page.");
        }

        return LandingPageMapper.toResponseDTO(landing);
    }
}