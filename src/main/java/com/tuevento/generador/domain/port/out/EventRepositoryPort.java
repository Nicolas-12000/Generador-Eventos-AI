package com.tuevento.generador.domain.port.out;

import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.domain.model.Event.EventStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para el repositorio de eventos
 * Define las operaciones de persistencia para la entidad Event
 */
public interface EventRepositoryPort {
    
    /**
     * Guarda un evento en el repositorio
     * @param event Evento a guardar
     * @return Evento guardado con ID asignado
     */
    Event save(Event event);
    
    /**
     * Busca un evento por su ID
     * @param id ID del evento
     * @return Optional con el evento encontrado o vacío
     */
    Optional<Event> findById(Long id);
    
    /**
     * Busca todos los eventos de un usuario específico
     * @param userId ID del usuario propietario
     * @return Lista de eventos del usuario
     */
    List<Event> findByUserId(UUID userId);
    
    /**
     * Busca eventos por usuario con paginación
     * @param userId ID del usuario
     * @param page Número de página (0-based)
     * @param size Tamaño de página
     * @return Lista de eventos paginada
     */
    List<Event> findByUserId(UUID userId, int page, int size);
    
    /**
     * Busca eventos por estado
     * @param status Estado del evento
     * @return Lista de eventos con el estado especificado
     */
    List<Event> findByStatus(EventStatus status);
    
    /**
     * Busca eventos por usuario y estado
     * @param userId ID del usuario
     * @param status Estado del evento
     * @return Lista de eventos filtrados
     */
    List<Event> findByUserIdAndStatus(UUID userId, EventStatus status);
    
    /**
     * Busca eventos por rango de fechas
     * @param startDate Fecha inicial
     * @param endDate Fecha final
     * @return Lista de eventos en el rango especificado
     */
    List<Event> findByEventDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Busca eventos por tipo
     * @param eventType Tipo de evento
     * @return Lista de eventos del tipo especificado
     */
    List<Event> findByEventType(String eventType);
    
    /**
     * Busca eventos por nombre (contiene texto)
     * @param namePattern Patrón de búsqueda en el nombre
     * @return Lista de eventos que coinciden
     */
    List<Event> findByNameContaining(String namePattern);
    
    /**
     * Busca eventos por usuario y nombre
     * @param userId ID del usuario
     * @param namePattern Patrón de búsqueda en el nombre
     * @return Lista de eventos que coinciden
     */
    List<Event> findByUserIdAndNameContaining(UUID userId, String namePattern);
    
    /**
     * Busca eventos próximos (fecha futura) por usuario
     * @param userId ID del usuario
     * @return Lista de eventos futuros del usuario
     */
    List<Event> findUpcomingEventsByUserId(UUID userId);
    
    /**
     * Busca eventos pasados por usuario
     * @param userId ID del usuario
     * @return Lista de eventos pasados del usuario
     */
    List<Event> findPastEventsByUserId(UUID userId);
    
    /**
     * Cuenta los eventos de un usuario
     * @param userId ID del usuario
     * @return Número total de eventos del usuario
     */
    long countByUserId(UUID userId);
    
    /**
     * Cuenta los eventos por estado
     * @param status Estado del evento
     * @return Número de eventos con el estado especificado
     */
    long countByStatus(EventStatus status);
    
    /**
     * Verifica si un evento pertenece a un usuario
     * @param eventId ID del evento
     * @param userId ID del usuario
     * @return true si el evento pertenece al usuario, false en caso contrario
     */
    boolean existsByIdAndUserId(Long eventId, UUID userId);
    
    /**
     * Obtiene todos los eventos con paginación
     * @param page Número de página (0-based)
     * @param size Tamaño de página
     * @return Lista de eventos paginada
     */
    List<Event> findAll(int page, int size);
    
    /**
     * Cuenta el total de eventos en el sistema
     * @return Número total de eventos
     */
    long count();
    
    /**
     * Elimina un evento por su ID
     * @param id ID del evento a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    boolean deleteById(Long id);
    
    /**
     * Elimina todos los eventos de un usuario
     * @param userId ID del usuario
     * @return Número de eventos eliminados
     */
    int deleteByUserId(UUID userId);
    
    /**
     * Actualiza un evento
     * @param event Evento con datos actualizados
     * @return Evento actualizado
     */
    Event update(Event event);
}