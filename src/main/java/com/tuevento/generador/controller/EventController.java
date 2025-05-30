package com.tuevento.generador.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tuevento.generador.application.dto.EventDto;
import com.tuevento.generador.service.EventService;
import org.springframework.http.HttpStatus;


import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/generate")
    public ResponseEntity<EventDto> generateEvent(
            @RequestParam String userMessage,
            @RequestParam String templateName,
            @RequestParam String organizerName,
            @RequestParam String organizerEmail
    ) {
        try {
            EventDto eventDto = eventService.generateEvent(userMessage, templateName, organizerName, organizerEmail);
            return ResponseEntity.ok(eventDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}