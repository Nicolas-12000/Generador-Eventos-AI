package com.tuevento.generador.application.usecase.user;

import com.tuevento.generador.application.dto.user.UserRegisterDTO;
import com.tuevento.generador.application.dto.user.UserResponseDTO;
import com.tuevento.generador.application.mapper.UserMapper;
import com.tuevento.generador.domain.model.User;
import com.tuevento.generador.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case para registro de nuevos usuarios.
 */
@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder    passwordEncoder;

    @Transactional
    public UserResponseDTO execute(UserRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Email already in use: " + dto.getEmail());
        }
        User user = User.builder()
            .username(dto.getName())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .build();
        User saved = userRepository.save(user);
        return UserMapper.toResponseDTO(saved);
    }
}
