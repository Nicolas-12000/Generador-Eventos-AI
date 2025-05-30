package com.tuevento.generador.controller;

import com.tuevento.generador.application.dto.SponsorDto;
import com.tuevento.generador.service.SponsorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sponsors")
@RequiredArgsConstructor
public class SponsorController {

    private final SponsorService sponsorService;

    @GetMapping
    public ResponseEntity<List<SponsorDto>> getAllSponsors() {
        return ResponseEntity.ok(sponsorService.getAllSponsors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SponsorDto> getSponsorById(@PathVariable UUID id) {
        return ResponseEntity.ok(sponsorService.getSponsorById(id));
    }

    @PostMapping
    public ResponseEntity<SponsorDto> createSponsor(@RequestBody SponsorDto dto) {
        SponsorDto created = sponsorService.createSponsor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SponsorDto> updateSponsor(@PathVariable UUID id, @RequestBody SponsorDto dto) {
        SponsorDto updated = sponsorService.updateSponsor(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable UUID id) {
        sponsorService.deleteSponsor(id);
        return ResponseEntity.noContent().build();
    }
}
