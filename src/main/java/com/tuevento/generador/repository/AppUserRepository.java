package com.tuevento.generador.repository;

import com.tuevento.generador.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    boolean existsByUsername(String username);
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);

}
