package com.tuevento.generador.repository;

import com.tuevento.generador.domain.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventRepository extends JpaRepository<Event, UUID> {

    /**
     * Lista todos los eventos creados por un organizador (su email), 
     * ordenados cronológicamente para mostrar historial.
     */
    List<Event> findAllByOrganizerEmailOrderByEventDateTimeDesc(String organizerEmail);


     Page<Event> findAllByOrganizerEmail(String organizerEmail, Pageable pg);

     List<Event> findByOrganizerName(String organizerName);

}