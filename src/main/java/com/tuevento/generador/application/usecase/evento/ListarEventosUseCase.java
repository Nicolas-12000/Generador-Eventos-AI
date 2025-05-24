package com.tuevento.generador.application.usecase.evento;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.domain.port.out.EventRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarEventosUseCase {

    private final EventRepositoryPort repository;

    public List<Event> listarPorUsuario(UUID userId) {
        return repository.findByUserId(userId);
    }
}