package com.tuevento.generador.repository;

import com.tuevento.generador.domain.model.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpeakerRepository extends JpaRepository<Speaker, UUID> {

    /**
     * Encuentra todos los ponentes que participan en un evento.
     */
    @Query("SELECT s FROM Speaker s JOIN s.events e WHERE e.id = :eventId")
    List<Speaker> findByEventId(@Param("eventId") UUID eventId);
}
