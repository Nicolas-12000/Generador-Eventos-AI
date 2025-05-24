package com.tuevento.generador.application.usecase.landing;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.port.out.LandingPageRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteLandingPageUseCase {

    private final LandingPageRepositoryPort repository;

    /**
     * Deletes a landing page if the current user owns it.
     *
     * @param landingPageId  the ID of the landing page to delete
     * @param currentUserId  the ID of the authenticated user
     */
    @Transactional
    public void execute(Long landingPageId, UUID currentUserId) {
        LandingPage landing = repository.findById(landingPageId)
            .orElseThrow(() -> new NoSuchElementException(
                "Landing page not found: " + landingPageId
            ));

        // ownership check
        if (landing.getEvent() == null ||
            !landing.getEvent().getUserId().equals(currentUserId)) {
            throw new SecurityException("Not authorized to delete this landing page.");
        }

        repository.deleteById(landingPageId);
    }
}