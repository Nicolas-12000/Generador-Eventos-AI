// src/main/java/com/tuevento/generador/application/usecase/user/UpdateUserProfileUseCase.java
package com.tuevento.generador.application.usecase.user;

import com.tuevento.generador.application.dto.user.UserUpdateDTO;
import com.tuevento.generador.application.dto.user.UserResponseDTO;
import com.tuevento.generador.application.mapper.UserMapper;
import com.tuevento.generador.domain.model.User;
import com.tuevento.generador.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {

    private final UserRepositoryPort userRepository;

    /**
     * Actualiza nombre y/o email del usuario.
     *
     * @param userId  ID del usuario autenticado
     * @param dto     datos opcionales a actualizar
     * @return        DTO con el usuario actualizado
     */
    @Transactional
    public UserResponseDTO execute(UUID userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado: " + userId));

        dto.getName()
           .filter(n -> !n.isBlank())
           .ifPresent(user::setUsername);

        dto.getEmail()
           .filter(email -> !email.isBlank())
           .ifPresent(newEmail -> {
               if (userRepository.existsByEmail(newEmail) && !newEmail.equals(user.getEmail())) {
                   throw new IllegalStateException("Email ya en uso: " + newEmail);
               }
               user.setEmail(newEmail);
           });

        User saved = userRepository.save(user);
        return UserMapper.toResponseDTO(saved);
    }
}
