package com.tuevento.generador.controller;

import com.tuevento.generador.application.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.tuevento.generador.service.EventService;
import com.tuevento.generador.application.dto.GenerateEventRequest;


@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
  private final EventService eventService;

  @PostMapping("/generate")
  public ResponseEntity<EventDto> generateEvent(
      @Valid @RequestBody GenerateEventRequest req,
      @AuthenticationPrincipal(expression="username") String username,
      @AuthenticationPrincipal(expression="email")    String email
  ) throws Exception {
    EventDto result = eventService.generateEvent(
       req.getUserMessage(),
       username,
       email
    );
    return ResponseEntity.ok(result);
  }
}
