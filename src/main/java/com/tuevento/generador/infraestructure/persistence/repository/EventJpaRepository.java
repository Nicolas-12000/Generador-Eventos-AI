package com.tuevento.generador.infraestructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.domain.model.Event.EventStatus;

/**
 * Repositorio JPA para la entidad Event
 * Extiende JpaRepository para operaciones CRUD básicas
 */
@Repository
public interface EventJpaRepository extends JpaRepository<Event, Long> {
    
    /**
     * Busca todos los eventos de un usuario específico
     */
    List<Event> findByUserId(UUID userId);
    
    /**
     * Busca eventos por usuario con paginación
     */
    Page<Event> findByUserId(UUID userId, Pageable pageable);
    
    /**
     * Busca eventos por estado
     */
    List<Event> findByStatus(EventStatus status);
    
    /**
     * Busca eventos por estado con paginación
     */
    Page<Event> findByStatus(EventStatus status, Pageable pageable);
    
    /**
     * Busca eventos por usuario y estado
     */
    List<Event> findByUserIdAndStatus(UUID userId, EventStatus status);
    
    /**
     * Busca eventos por usuario y estado con paginación
     */
    Page<Event> findByUserIdAndStatus(UUID userId, EventStatus status, Pageable pageable);
    
    /**
     * Busca eventos por rango de fechas
     */
    List<Event> findByEventDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Busca eventos por rango de fechas con paginación
     */
    Page<Event> findByEventDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    /**
     * Busca eventos por tipo
     */
    List<Event> findByEventType(String eventType);
    
    /**
     * Busca eventos por tipo con paginación
     */
    Page<Event> findByEventType(String eventType, Pageable pageable);
    
    /**
     * Busca eventos por nombre (contiene texto)
     */
    List<Event> findByNameContainingIgnoreCase(String namePattern);
    
    /**
     * Busca eventos por nombre (contiene texto) con paginación
     */
    Page<Event> findByNameContainingIgnoreCase(String namePattern, Pageable pageable);
    
    /**
     * Busca eventos por usuario y nombre
     */
    List<Event> findByUserIdAndNameContainingIgnoreCase(UUID userId, String namePattern);
    
    /**
     * Busca eventos por usuario y nombre con paginación
     */
    Page<Event> findByUserIdAndNameContainingIgnoreCase(UUID userId, String namePattern, Pageable pageable);
    
    /**
     * Busca eventos próximos (fecha futura) por usuario
     */
    @Query("SELECT e FROM Event e WHERE e.userId = :userId AND e.eventDateTime > :currentDate ORDER BY e.eventDateTime ASC")
    List<Event> findUpcomingEventsByUserId(@Param("userId") UUID userId, @Param("currentDate") LocalDateTime currentDate);
    
    /**
     * Busca eventos próximos (fecha futura) por usuario con paginación
     */
    @Query("SELECT e FROM Event e WHERE e.userId = :userId AND e.eventDateTime > :currentDate ORDER BY e.eventDateTime ASC")
    Page<Event> findUpcomingEventsByUserId(@Param("userId") UUID userId, @Param("currentDate") LocalDateTime currentDate, Pageable pageable);
    
    /**
     * Busca eventos pasados por usuario
     */
    @Query("SELECT e FROM Event e WHERE e.userId = :userId AND e.eventDateTime < :currentDate ORDER BY e.eventDateTime DESC")
    List<Event> findPastEventsByUserId(@Param("userId") UUID userId, @Param("currentDate") LocalDateTime currentDate);
    
    /**
     * Busca eventos pasados por usuario con paginación
     */
    @Query("SELECT e FROM Event e WHERE e.userId = :userId AND e.eventDateTime < :currentDate ORDER BY e.eventDateTime DESC")
    Page<Event> findPastEventsByUserId(@Param("userId") UUID userId, @Param("currentDate") LocalDateTime currentDate, Pageable pageable);
    
    /**
     * Cuenta los eventos de un usuario
     */
    long countByUserId(UUID userId);
    
    /**
     * Cuenta los eventos por estado
     */
    long countByStatus(EventStatus status);
    
    /**
     * Cuenta los eventos por usuario y estado
     */
    long countByUserIdAndStatus(UUID userId, EventStatus status);
    
    /**
     * Verifica si un evento pertenece a un usuario
     */
    boolean existsByIdAndUserId(Long eventId, UUID userId);
    
    /**
     * Elimina todos los eventos de un usuario
     */
    int deleteByUserId(UUID userId);
    
    /**
     * Busca eventos por múltiples criterios
     */
    @Query("SELECT e FROM Event e WHERE " +
           "(:userId IS NULL OR e.userId = :userId) AND " +
           "(:status IS NULL OR e.status = :status) AND " +
           "(:eventType IS NULL OR e.eventType = :eventType) AND " +
           "(:startDate IS NULL OR e.eventDateTime >= :startDate) AND " +
           "(:endDate IS NULL OR e.eventDateTime <= :endDate) AND " +
           "(:searchTerm IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           " LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Event> findEventsByCriteria(
            @Param("userId") UUID userId,
            @Param("status") EventStatus status,
            @Param("eventType") String eventType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("searchTerm") String searchTerm,
            Pageable pageable);
    
    /**
     * Busca eventos próximos públicos (publicados)
     */
    @Query("SELECT e FROM Event e WHERE e.status = 'PUBLISHED' AND e.eventDateTime > :currentDate ORDER BY e.eventDateTime ASC")
    List<Event> findUpcomingPublicEvents(@Param("currentDate") LocalDateTime currentDate);
    
    /**
     * Busca eventos próximos públicos con paginación
     */
    @Query("SELECT e FROM Event e WHERE e.status = 'PUBLISHED' AND e.eventDateTime > :currentDate ORDER BY e.eventDateTime ASC")
    Page<Event> findUpcomingPublicEvents(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);
}
