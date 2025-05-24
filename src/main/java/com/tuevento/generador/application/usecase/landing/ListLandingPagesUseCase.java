package com.tuevento.generador.application.usecase.landing;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuevento.generador.application.dto.landing.LandingPageResponseDTO;
import com.tuevento.generador.application.mapper.LandingPageMapper;
import com.tuevento.generador.domain.port.out.LandingPageRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListLandingPagesUseCase {

    private final LandingPageRepositoryPort repository;

    /**
     * Lists all landing pages for the given user.
     *
     * @param currentUserId  the ID of the authenticated user
     * @return               list of DTOs for that user's landing pages
     */
    @Transactional(readOnly = true)
    public List<LandingPageResponseDTO> execute(UUID currentUserId) {
        return repository.findByUserId(currentUserId).stream()
            .map(LandingPageMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
}