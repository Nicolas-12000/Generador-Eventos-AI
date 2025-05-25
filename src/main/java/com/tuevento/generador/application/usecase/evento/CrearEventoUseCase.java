package com.tuevento.generador.application.usecase.evento;

import com.tuevento.generador.application.dto.evento.EventoCreateDTO;
import com.tuevento.generador.application.dto.evento.EventoResponseDTO;
import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.infraestructure.persistence.repository.EventJpaRepository;
import com.tuevento.generador.infraestructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso para crear un nuevo evento
 * Incluye validaciones de negocio y manejo de errores
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CrearEventoUseCase {
    
    private final EventJpaRepository eventRepository;
    private final UserJpaRepository userRepository;
    
    /**
     * Crea un nuevo evento para el usuario especificado
     * 
     * @param dto Datos del evento a crear
     * @param usuarioId ID del usuario que crea el evento
     * @return DTO con los datos del evento creado
     * @throws EventoBusinessException si hay errores de validación de negocio
     */
    @Transactional
    public EventoResponseDTO execute (EventoCreateDTO dto, UUID usuarioId) {
        log.info("Iniciando creación de evento para usuario: {}", usuarioId);
        
        // Validar que el usuario existe
        if (!userRepository.existsById(usuarioId)) {
            throw new EventoBusinessException("Usuario no encontrado: " + usuarioId);
        }
        
        // Validar reglas de negocio
        validarReglasDeNegocio(dto, usuarioId);
        
        // Crear la entidad Event
        Event nuevoEvento = construirEvento(dto, usuarioId);
        
        // Guardar en base de datos
        Event eventoGuardado = eventRepository.save(nuevoEvento);
        
        log.info("Evento creado exitosamente con ID: {}", eventoGuardado.getId());
        
        return EventoResponseDTO.fromEntity(eventoGuardado);
    }
    
    /**
     * Valida las reglas de negocio específicas para la creación de eventos
     */
    private void validarReglasDeNegocio(EventoCreateDTO dto, UUID usuarioId) {
        // Validar que la fecha no sea en el pasado (validación adicional)
        if (dto.getEventDateTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new EventoBusinessException("El evento debe programarse con al menos 30 minutos de anticipación");
        }
        
        // Validar que no exista un evento con el mismo nombre para este usuario
        if (eventRepository.existsByUserIdAndNameIgnoreCase(usuarioId, dto.getName())) {
            throw new EventoBusinessException("Ya existe un evento con el mismo nombre para este usuario");
        }
        
        // Validar límites de eventos por usuario (ejemplo: máximo 50 eventos activos)
        long eventosActivos = eventRepository.countByUserId(usuarioId);
        if (eventosActivos >= 50) {
            throw new EventoBusinessException("Has alcanzado el límite máximo de eventos (50). Elimina eventos antiguos antes de crear nuevos.");
        }
    }
    
    /**
     * Construye la entidad Event a partir del DTO
     */
    private Event construirEvento(EventoCreateDTO dto, UUID usuarioId) {
        return Event.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .eventDateTime(dto.getEventDateTime())
                .locationDetails(dto.getLocationDetails())
                .locationAddress(dto.getLocationAddress())
                .userId(usuarioId)
                .organizerName(dto.getOrganizerName())
                .organizerEmail(dto.getOrganizerEmail())
                .organizerPhone(dto.getOrganizerPhone())
                .eventType(dto.getEventType())
                .ticketUrl(dto.getTicketUrl())
                .eventImageUrl(dto.getEventImageUrl())
                .status(Event.EventStatus.DRAFT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Excepción personalizada para errores de negocio en eventos
     */
    public static class EventoBusinessException extends RuntimeException {
        public EventoBusinessException(String message) {
            super(message);
        }
    }
}