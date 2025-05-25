package com.tuevento.generador.infraestructure.web;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tuevento.generador.application.dto.landing.LandingPageCreateDTO;
import com.tuevento.generador.application.dto.landing.LandingPageResponseDTO;
import com.tuevento.generador.application.dto.landing.LandingPageUpdateDTO;
import com.tuevento.generador.application.usecase.landing.CreateLandingPageUseCase;
import com.tuevento.generador.application.usecase.landing.DeleteLandingPageUseCase;
import com.tuevento.generador.application.usecase.landing.GetLandingPageUseCase;
import com.tuevento.generador.application.usecase.landing.ListLandingPagesUseCase;
import com.tuevento.generador.application.usecase.landing.UpdateLandingPageUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/landings")
@Validated
@RequiredArgsConstructor
public class LandingPageController {

    private final CreateLandingPageUseCase createUseCase;
    private final GetLandingPageUseCase    getUseCase;
    private final ListLandingPagesUseCase  listUseCase;
    private final UpdateLandingPageUseCase updateUseCase;
    private final DeleteLandingPageUseCase deleteUseCase;

    @PostMapping
    public ResponseEntity<LandingPageResponseDTO> create(
            @RequestBody @Valid LandingPageCreateDTO dto,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        LandingPageResponseDTO created = createUseCase.execute(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<LandingPageResponseDTO>> list(
            @RequestHeader("X-User-Id") UUID userId
    ) {
        List<LandingPageResponseDTO> pages = listUseCase.execute(userId);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LandingPageResponseDTO> get(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        LandingPageResponseDTO dto = getUseCase.execute(id, userId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LandingPageResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid LandingPageUpdateDTO dto,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        LandingPageResponseDTO updated = updateUseCase.execute(id, dto, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        deleteUseCase.execute(id, userId);
    }
}