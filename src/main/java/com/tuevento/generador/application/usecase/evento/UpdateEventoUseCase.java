package com.tuevento.generador.application.usecase.evento;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuevento.generador.application.dto.evento.EventoResponseDTO;
import com.tuevento.generador.application.dto.evento.EventoUpdateDTO;
import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.infraestructure.persistence.repository.EventJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso para actualizar un evento existente
 * Implementa actualización parcial usando Optional
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateEventoUseCase {
    
    private final EventJpaRepository eventRepository;
    
    /**
     * Actualiza un evento existente con los datos proporcionados
     * 
     * @param eventoId ID del evento a actualizar
     * @param dto Datos a actualizar (campos opcionales)
     * @param usuarioId ID del usuario que solicita la actualización
     * @return DTO con los datos del evento actualizado
     * @throws EventoNotFoundException si el evento no existe
     * @throws EventoAccessDeniedException si el usuario no es propietario
     * @throws EventoBusinessException si hay errores de validación
     */
    @Transactional
    public EventoResponseDTO ejecutar(Long eventoId, EventoUpdateDTO dto, UUID usuarioId) {
        log.info("Iniciando actualización de evento ID: {} por usuario: {}", eventoId, usuarioId);
        
        // Validar que hay algo que actualizar
        if (!dto.hasUpdates()) {
            throw new EventoBusinessException("No se proporcionaron campos para actualizar");
        }
        
        // Buscar el evento y verificar propiedad
        Event evento = eventRepository.findByIdAndUserId(eventoId, usuarioId);
        if (evento == null) {
            throw new EventoNotFoundException("Evento no encontrado o no pertenece al usuario: " + eventoId);
        }
        
        // Validar que el evento se puede actualizar
        validarEventoActualizable(evento);
        
        // Aplicar actualizaciones
        aplicarActualizaciones(evento, dto);
        
        // Validar reglas de negocio después de las actualizaciones
        validarReglasDeNegocio(evento, dto);
        
        // Guardar cambios
        Event eventoActualizado = eventRepository.save(evento);
        
        log.info("Evento actualizado exitosamente ID: {}", eventoId);
        
        return EventoResponseDTO.fromEntity(eventoActualizado);
    }
    
    /**
     * Valida que el evento se puede actualizar
     */
    private void validarEventoActualizable(Event evento) {
        // No permitir actualizar eventos completados o cancelados
        if (evento.getStatus() == Event.EventStatus.COMPLETED) {
            throw new EventoBusinessException("No se puede actualizar un evento completado");
        }
        
        if (evento.getStatus() == Event.EventStatus.CANCELLED) {
            throw new EventoBusinessException("No se puede actualizar un evento cancelado");
        }
        
        // No permitir actualizar eventos que ya pasaron
        if (evento.getEventDateTime().isBefore(LocalDateTime.now())) {
            throw new EventoBusinessException("No se puede actualizar un evento que ya ocurrió");
        }
    }
    
    /**
     * Aplica las actualizaciones del DTO a la entidad
     */
    private void aplicarActualizaciones(Event evento, EventoUpdateDTO dto) {
        dto.getName().ifPresent(evento::setName);
        dto.getDescription().ifPresent(evento::setDescription);
        dto.getEventDateTime().ifPresent(evento::setEventDateTime);
        dto.getLocationDetails().ifPresent(evento::setLocationDetails);
        dto.getLocationAddress().ifPresent(evento::setLocationAddress);
        dto.getOrganizerName().ifPresent(evento::setOrganizerName);
        dto.getOrganizerEmail().ifPresent(evento::setOrganizerEmail);
        dto.getOrganizerPhone().ifPresent(evento::setOrganizerPhone);
        dto.getEventType().ifPresent(evento::setEventType);
        dto.getTicketUrl().ifPresent(evento::setTicketUrl);
        dto.getEventImageUrl().ifPresent(evento::setEventImageUrl);
        
        // Actualizar timestamp
        evento.setUpdatedAt(LocalDateTime.now());
    }
    
    /**
     * Valida reglas de negocio después de aplicar actualizaciones
     */
    private void validarReglasDeNegocio(Event evento, EventoUpdateDTO dto) {
        // Si se actualiza la fecha, validar que sea futura
        if (dto.getEventDateTime().isPresent()) {
            if (evento.getEventDateTime().isBefore(LocalDateTime.now().plusMinutes(30))) {
                throw new EventoBusinessException("El evento debe programarse con al menos 30 minutos de anticipación");
            }
        }
        
        // Si se actualiza el nombre, validar unicidad
        if (dto.getName().isPresent()) {
            boolean existeOtroConMismoNombre = eventRepository.existsByUserIdAndNameIgnoreCase(evento.getUserId(), dto.getName().get());
            
            if (existeOtroConMismoNombre) {
                throw new EventoBusinessException("Ya existe otro evento con el nombre: " + evento.getName());
            }
        }
    }
    
    /**
     * Excepciones personalizadas
     */
    public static class EventoNotFoundException extends RuntimeException {
        public EventoNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class EventoAccessDeniedException extends RuntimeException {
        public EventoAccessDeniedException(String message) {
            super(message);
        }
    }
    
    public static class EventoBusinessException extends RuntimeException {
        public EventoBusinessException(String message) {
            super(message);
        }
    }
}