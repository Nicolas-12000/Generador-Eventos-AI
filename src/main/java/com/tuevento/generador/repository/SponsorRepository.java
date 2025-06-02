package com.tuevento.generador.repository;

import com.tuevento.generador.domain.model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SponsorRepository extends JpaRepository<Sponsor, UUID> {

    /**
     * Lista los patrocinadores de un evento,
     * para mostrarlos en la sección de logos de la landing.
     */
    List<Sponsor> findByEvents_Id(UUID eventId);
}
