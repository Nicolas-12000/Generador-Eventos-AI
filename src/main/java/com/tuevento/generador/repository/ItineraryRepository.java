package com.tuevento.generador.repository;

import com.tuevento.generador.domain.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ItineraryRepository extends JpaRepository<Itinerary, UUID> {

    /**
     * Obtiene la agenda de un evento determinado, 
     * ordenada por hora de inicio para renderizar en la landing.
     */
    List<Itinerary> findAllByEventIdOrderByStartTimeAsc(UUID eventId);
}
