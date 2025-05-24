package com.tuevento.generador.application.usecase.evento;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.domain.port.out.EventRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObtenerEventoUseCase {

    private final EventRepositoryPort repository;

    public Event obtener(Long eventId, UUID userId) {
        return repository.findById(eventId)
                .filter(event -> event.getUserId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado o no autorizado."));
    }
}