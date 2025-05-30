package com.tuevento.generador.controller;

import com.tuevento.generador.application.dto.SpeakerDto;
import com.tuevento.generador.service.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/speakers")
@RequiredArgsConstructor
public class SpeakerController {

    private final SpeakerService speakerService;

    @GetMapping
    public ResponseEntity<List<SpeakerDto>> getAllSpeakers() {
        return ResponseEntity.ok(speakerService.getAllSpeakers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpeakerDto> getSpeakerById(@PathVariable UUID id) {
        return ResponseEntity.ok(speakerService.getSpeakerById(id));
    }

    @PostMapping
    public ResponseEntity<SpeakerDto> createSpeaker(@RequestBody SpeakerDto dto) {
        SpeakerDto created = speakerService.createSpeaker(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpeakerDto> updateSpeaker(@PathVariable UUID id, @RequestBody SpeakerDto dto) {
        SpeakerDto updated = speakerService.updateSpeaker(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpeaker(@PathVariable UUID id) {
        speakerService.deleteSpeaker(id);
        return ResponseEntity.noContent().build();
    }
}
