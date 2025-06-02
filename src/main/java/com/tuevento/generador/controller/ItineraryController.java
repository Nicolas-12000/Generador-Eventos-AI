package com.tuevento.generador.controller;

import com.tuevento.generador.application.dto.ItineraryDto;
import com.tuevento.generador.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/itineraries")
@RequiredArgsConstructor
public class ItineraryController {

    private final ItineraryService itineraryService;

    @GetMapping
    public ResponseEntity<List<ItineraryDto>> getAllItineraries() {
        return ResponseEntity.ok(itineraryService.getAllItineraries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItineraryDto> getItineraryById(@PathVariable UUID id) {
        return ResponseEntity.ok(itineraryService.getItineraryById(id));
    }

    @PostMapping
    public ResponseEntity<ItineraryDto> createItinerary(@RequestBody ItineraryDto dto) {
        ItineraryDto created = itineraryService.createItinerary(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItineraryDto> updateItinerary(@PathVariable UUID id, @RequestBody ItineraryDto dto) {
        ItineraryDto updated = itineraryService.updateItinerary(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItinerary(@PathVariable UUID id) {
        itineraryService.deleteItinerary(id);
        return ResponseEntity.noContent().build();
    }
}
