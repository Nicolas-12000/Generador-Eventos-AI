package com.tuevento.generador.application.usecase.landing;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuevento.generador.application.dto.landing.LandingPageResponseDTO;
import com.tuevento.generador.application.dto.landing.LandingPageUpdateDTO;
import com.tuevento.generador.application.mapper.LandingPageMapper;
import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.port.out.LandingPageRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateLandingPageUseCase {

    private final LandingPageRepositoryPort repository;

    /**
     * Updates an existing landing page.
     *
     * @param landingPageId  the ID of the landing page to update
     * @param updateDTO      the fields to update
     * @param currentUserId  the ID of the authenticated user
     * @return               the updated landing page DTO
     */
    @Transactional
    public LandingPageResponseDTO execute(Long landingPageId,
                                          LandingPageUpdateDTO updateDTO,
                                          UUID currentUserId) {
        LandingPage landing = repository.findById(landingPageId)
            .orElseThrow(() -> new NoSuchElementException(
                "Landing page not found: " + landingPageId
            ));

        // ensure the user owns the underlying event
        if (landing.getEvent() == null ||
            !landing.getEvent().getUserId().equals(currentUserId)) {
            throw new SecurityException("Not authorized to update this landing page.");
        }

        // apply only provided updates
        LandingPageMapper.updateEntity(landing, updateDTO);

        // business validations (e.g. if showMap then event has address)…
        if (landing.isShowMap() &&
            (landing.getEvent().getLocationAddress() == null ||
             landing.getEvent().getLocationAddress().isBlank())) {
            throw new IllegalStateException(
                "Cannot show map without event address."
            );
        }
        if (landing.isShowSpeakers() &&
            landing.getEvent().getSpeakers().isEmpty()) {
            throw new IllegalStateException(
                "Cannot show speakers without associated speakers."
            );
        }
        if (landing.isShowSchedule() &&
            landing.getEvent().getSchedule().isEmpty()) {
            throw new IllegalStateException(
                "Cannot show schedule without schedule items."
            );
        }

        LandingPage updated = repository.save(landing);
        return LandingPageMapper.toResponseDTO(updated);
    }
}