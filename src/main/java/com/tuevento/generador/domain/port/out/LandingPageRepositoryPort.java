package com.tuevento.generador.domain.port.out;

import com.tuevento.generador.domain.model.LandingPage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para persistencia de LandingPage.
 * Define operaciones mínimas que el adaptador debe implementar.
 */
public interface LandingPageRepositoryPort {

    /**
     * Guarda o actualiza una LandingPage.
     * @param landingPage entidad a persistir
     * @return la entidad guardada
     */
    LandingPage save(LandingPage landingPage);

    /**
     * Busca una LandingPage por su ID.
     */
    Optional<LandingPage> findById(Long id);

    /**
     * Busca una LandingPage por el ID de su evento asociado.
     */
    Optional<LandingPage> findByEventId(Long eventId);

    /**
     * Lista todas las LandingPages de un usuario.
     */
    List<LandingPage> findByUserId(UUID userId);

    /**
     * Elimina la LandingPage con el ID dado.
     */
    void deleteById(Long id);
}
