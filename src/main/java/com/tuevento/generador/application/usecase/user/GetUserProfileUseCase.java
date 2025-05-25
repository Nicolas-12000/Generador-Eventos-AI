package com.tuevento.generador.application.usecase.user;

import com.tuevento.generador.application.dto.user.UserResponseDTO;
import com.tuevento.generador.application.mapper.UserMapper;
import com.tuevento.generador.domain.model.User;
import com.tuevento.generador.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Use case para obtener el perfil del usuario autenticado.
 */
@Service
@RequiredArgsConstructor
public class GetUserProfileUseCase {

    private final UserRepositoryPort userRepository;

    @Transactional(readOnly = true)
    public UserResponseDTO execute(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));
        return UserMapper.toResponseDTO(user);
    }
}
