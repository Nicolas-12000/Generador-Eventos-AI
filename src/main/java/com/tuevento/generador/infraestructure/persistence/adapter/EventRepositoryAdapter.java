package com.tuevento.generador.infraestructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.domain.model.Event.EventStatus;
import com.tuevento.generador.domain.port.out.EventRepositoryPort;
import com.tuevento.generador.infraestructure.persistence.repository.EventJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador que implementa el puerto EventRepositoryPort
 * Conecta el dominio con la infraestructura de persistencia JPA
 */
@Component
@RequiredArgsConstructor
public class EventRepositoryAdapter implements EventRepositoryPort {
    
    private final EventJpaRepository eventJpaRepository;
    
    @Override
    public Event save(Event event) {
        return eventJpaRepository.save(event);
    }
    
    @Override
    public Optional<Event> findById(Long id) {
        return eventJpaRepository.findById(id);
    }
    
    @Override
    public List<Event> findByUserId(UUID userId) {
        return eventJpaRepository.findByUserId(userId);
    }
    
    @Override
    public List<Event> findByUserId(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findByUserId(userId, pageable).getContent();
    }
    
    @Override
    public List<Event> findByStatus(EventStatus status) {
        return eventJpaRepository.findByStatus(status);
    }
    
    @Override
    public List<Event> findByUserIdAndStatus(UUID userId, EventStatus status) {
        return eventJpaRepository.findByUserIdAndStatus(userId, status);
    }
    
    @Override
    public List<Event> findByEventDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return eventJpaRepository.findByEventDateTimeBetween(startDate, endDate);
    }
    
    @Override
    public List<Event> findByEventType(String eventType) {
        return eventJpaRepository.findByEventType(eventType);
    }
    
    @Override
    public List<Event> findByNameContaining(String namePattern) {
        return eventJpaRepository.findByNameContainingIgnoreCase(namePattern);
    }
    
    @Override
    public List<Event> findByUserIdAndNameContaining(UUID userId, String namePattern) {
        return eventJpaRepository.findByUserIdAndNameContainingIgnoreCase(userId, namePattern);
    }
    
    @Override
    public List<Event> findUpcomingEventsByUserId(UUID userId) {
        return eventJpaRepository.findUpcomingEventsByUserId(userId, LocalDateTime.now());
    }
    
    @Override
    public List<Event> findPastEventsByUserId(UUID userId) {
        return eventJpaRepository.findPastEventsByUserId(userId, LocalDateTime.now());
    }
    
    @Override
    public long countByUserId(UUID userId) {
        return eventJpaRepository.countByUserId(userId);
    }
    
    @Override
    public long countByStatus(EventStatus status) {
        return eventJpaRepository.countByStatus(status);
    }
    
    @Override
    public boolean existsByIdAndUserId(Long eventId, UUID userId) {
        return eventJpaRepository.existsByIdAndUserId(eventId, userId);
    }
    
    @Override
    public List<Event> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findAll(pageable).getContent();
    }
    
    @Override
    public long count() {
        return eventJpaRepository.count();
    }
    
    @Override
    public boolean deleteById(Long id) {
        if (eventJpaRepository.existsById(id)) {
            eventJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    public int deleteByUserId(UUID userId) {
        return eventJpaRepository.deleteByUserId(userId);
    }
    
    @Override
    public Event update(Event event) {
        return eventJpaRepository.save(event);
    }
    
    /**
     * Métodos adicionales específicos del adaptador
     */
    
    public List<Event> findByStatusWithPagination(EventStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findByStatus(status, pageable).getContent();
    }
    
    public List<Event> findByUserIdAndStatusWithPagination(UUID userId, EventStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findByUserIdAndStatus(userId, status, pageable).getContent();
    }
    
    public List<Event> findByEventDateTimeBetweenWithPagination(LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findByEventDateTimeBetween(startDate, endDate, pageable).getContent();
    }
    
    public List<Event> findByEventTypeWithPagination(String eventType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findByEventType(eventType, pageable).getContent();
    }
    
    public List<Event> findByNameContainingWithPagination(String namePattern, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findByNameContainingIgnoreCase(namePattern, pageable).getContent();
    }
    
    public List<Event> findByUserIdAndNameContainingWithPagination(UUID userId, String namePattern, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findByUserIdAndNameContainingIgnoreCase(userId, namePattern, pageable).getContent();
    }
    
    public List<Event> findUpcomingEventsByUserIdWithPagination(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findUpcomingEventsByUserId(userId, LocalDateTime.now(), pageable).getContent();
    }
    
    public List<Event> findPastEventsByUserIdWithPagination(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findPastEventsByUserId(userId, LocalDateTime.now(), pageable).getContent();
    }
    
    public List<Event> findEventsByCriteria(UUID userId, EventStatus status, String eventType, 
                                          LocalDateTime startDate, LocalDateTime endDate, 
                                          String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findEventsByCriteria(userId, status, eventType, startDate, endDate, searchTerm, pageable).getContent();
    }
    
    public List<Event> findUpcomingPublicEvents() {
        return eventJpaRepository.findUpcomingPublicEvents(LocalDateTime.now());
    }
    
    public List<Event> findUpcomingPublicEventsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventJpaRepository.findUpcomingPublicEvents(LocalDateTime.now(), pageable).getContent();
    }
    
    public long countByUserIdAndStatus(UUID userId, EventStatus status) {
        return eventJpaRepository.countByUserIdAndStatus(userId, status);
    }
}