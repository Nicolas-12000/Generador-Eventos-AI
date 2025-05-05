package com.tuevento.generador.domain.ports;

import com.tuevento.generador.domain.model.Users;
import java.util.UUID;

public interface UserUseCase {
    Users registerUser(Users user);
    Users login(String email, String password);
    void updateUserProfile(UUID userId, Users user);
    Users getUserById(UUID userId);
}