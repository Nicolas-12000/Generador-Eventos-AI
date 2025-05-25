package com.tuevento.generador.infraestructure.persistence.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuevento.generador.domain.model.LandingPage;

/**
 * JPA repository para la entidad LandingPage.
 * Define métodos de consulta basados en convenciones de Spring Data.
 */
@Repository
public interface LandingPageJpaRepository extends JpaRepository<LandingPage, Long> {

    /**
     * Busca la landing page asociada a un evento.
     */
    Optional<LandingPage> findByEventId(Long eventId);

    /**
     * Lista las landing pages cuyo evento pertenece al usuario dado.
     * Utiliza la propiedad event.userId (UUID) de la entidad Event.
     */
    List<LandingPage> findByEventUserId(UUID userId);
}