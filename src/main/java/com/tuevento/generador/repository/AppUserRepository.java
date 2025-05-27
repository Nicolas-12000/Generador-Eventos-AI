package com.tuevento.generador.repository;

import com.tuevento.generador.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    /** Para registrar: verificar unicidad de username */
    boolean existsByUsername(String username);

    /** Para login y validación de token */
    Optional<AppUser> findByUsername(String username);
}
