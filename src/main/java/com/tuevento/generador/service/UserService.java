package com.tuevento.generador.service;

import com.tuevento.generador.domain.model.AppUser;
import com.tuevento.generador.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;

    public AppUser getUserByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username: " + username));
    }
}
