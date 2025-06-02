package com.tuevento.generador.repository;

import com.tuevento.generador.domain.model.LandingPage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LandingPageRepository extends JpaRepository<LandingPage, Long> {

    Optional<LandingPage> findByOrganizerUsername(String organizerUsername);

    boolean existsByOrganizerUsername(String organizerUsername);

}
