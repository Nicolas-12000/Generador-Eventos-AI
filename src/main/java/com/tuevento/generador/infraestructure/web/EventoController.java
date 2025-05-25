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

import com.tuevento.generador.application.dto.evento.EventoCreateDTO;
import com.tuevento.generador.application.dto.evento.EventoResponseDTO;
import com.tuevento.generador.application.dto.evento.EventoUpdateDTO;
import com.tuevento.generador.application.usecase.evento.UpdateEventoUseCase;
import com.tuevento.generador.application.usecase.evento.CrearEventoUseCase;
import com.tuevento.generador.application.usecase.evento.DeleteEventoUseCase;
import com.tuevento.generador.application.usecase.evento.ListarEventosUseCase;
import com.tuevento.generador.application.usecase.evento.GetEventoUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@Validated
@RequiredArgsConstructor
public class EventoController {

    private final CrearEventoUseCase    crearUseCase;
    private final GetEventoUseCase  obtenerUseCase;
    private final ListarEventosUseCase  listarUseCase;
    private final UpdateEventoUseCase actualizarUseCase;
    private final DeleteEventoUseCase   eliminarUseCase;

    @PostMapping
    public ResponseEntity<EventoResponseDTO> create(
            @RequestBody @Valid EventoCreateDTO dto,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        EventoResponseDTO created = crearUseCase.execute(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> list(
            @RequestHeader("X-User-Id") UUID userId
    ) {
        List<EventoResponseDTO> events = listarUseCase.listarPorUsuario(userId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> get(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        EventoResponseDTO dto = obtenerUseCase.execute(id, userId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid EventoUpdateDTO dto,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        EventoResponseDTO updated = actualizarUseCase.execute(id, dto, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") UUID userId
    ) {
        eliminarUseCase.eliminar(id, userId);
    }
}