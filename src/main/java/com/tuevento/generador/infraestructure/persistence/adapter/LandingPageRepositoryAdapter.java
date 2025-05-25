package com.tuevento.generador.infraestructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.port.out.LandingPageRepositoryPort;
import com.tuevento.generador.infraestructure.persistence.repository.LandingPageJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador JPA para LandingPageRepositoryPort.
 */
@Component
@RequiredArgsConstructor
public class LandingPageRepositoryAdapter implements LandingPageRepositoryPort {

    private final LandingPageJpaRepository landingPageJpaRepository;

    @Override
    public LandingPage save(LandingPage landingPage) {
        return landingPageJpaRepository.save(landingPage);
    }

    @Override
    public Optional<LandingPage> findById(Long id) {
        return landingPageJpaRepository.findById(id);
    }

    @Override
    public Optional<LandingPage> findByEventId(Long eventId) {
        return landingPageJpaRepository.findByEventId(eventId);
    }

    @Override
    public List<LandingPage> findByUserId(UUID userId) {
        return landingPageJpaRepository.findByEventUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        landingPageJpaRepository.deleteById(id);
    }
}

