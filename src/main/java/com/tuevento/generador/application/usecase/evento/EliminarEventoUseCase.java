package com.tuevento.generador.application.usecase.evento;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tuevento.generador.domain.port.out.EventRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EliminarEventoUseCase {

    private final EventRepositoryPort repository;

    public void eliminar(Long eventId, UUID userId) {
        boolean pertenece = repository.existsByIdAndUserId(eventId, userId);
        if (!pertenece) {
            throw new IllegalArgumentException("No autorizado o evento no encontrado.");
        }

        boolean eliminado = repository.deleteById(eventId);
        if (!eliminado) {
            throw new IllegalStateException("No se pudo eliminar el evento.");
        }
    }
}