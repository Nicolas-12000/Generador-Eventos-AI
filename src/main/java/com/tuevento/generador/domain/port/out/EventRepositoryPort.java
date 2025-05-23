package com.tuevento.generador.domain.port.out;

import java.util.List;
import java.util.Optional;
import com.tuevento.generador.domain.model.Event;

/**
 * Puerto de salida para operaciones de persistencia de Event
 */
public interface EventRepositoryPort {
    
    /**
     * Guarda un evento
     */
    Event save(Event event);
    
    /**
     * Busca un evento por ID
     */
    Optional<Event> findById(Long id);
    
    /**
     * Busca eventos por usuario
     */
    List<Event> findByUserId(Long userId);
    
    /**
     * Busca eventos por usuario con paginación
     */
    List<Event> findByUserIdWithPagination(Long userId, int page, int size);
    
    /**
     * Busca eventos por estado
     */
    List<Event> findByStatus(String status);
    
    /**
     * Busca eventos próximos por usuario
     */
    List<Event> findUpcomingEventsByUserId(Long userId);
    
    /**
     * Cuenta eventos por usuario
     */
    Long countByUserId(Long userId);
    
    /**
     * Verifica si existe un evento con ese nombre para el usuario
     */
    boolean existsByNameAndUserId(String name, Long userId);
    
    /**
     * Elimina un evento por ID
     */
    void deleteById(Long id);
    
    /**
     * Elimina todos los eventos de un usuario
     */
    void deleteByUserId(Long userId);
}
