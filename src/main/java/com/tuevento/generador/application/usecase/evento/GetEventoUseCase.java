package com.tuevento.generador.application.usecase.evento;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuevento.generador.application.dto.evento.EventoResponseDTO;
import com.tuevento.generador.application.mapper.EventMapper;
import com.tuevento.generador.domain.port.out.EventRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetEventoUseCase {

    private final EventRepositoryPort repository;

    /**
     * Obtiene un evento por ID y lo mapea a DTO.
     *
     * @param eventId ID del evento a buscar
     * @param userId  ID del usuario propietario
     * @return        EventoResponseDTO
     */
    @Transactional(readOnly = true)
    public EventoResponseDTO execute (Long eventId, UUID userId) {
        return repository.findById(eventId)
            .filter(e -> e.getUserId().equals(userId))
            .map(EventMapper::toResponseDTO)
            .orElseThrow(() -> new IllegalArgumentException(
                "Evento no encontrado o no autorizado."
            ));
    }
}