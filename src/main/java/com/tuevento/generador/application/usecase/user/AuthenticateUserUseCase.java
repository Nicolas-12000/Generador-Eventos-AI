package com.tuevento.generador.application.usecase.user;

import com.tuevento.generador.application.dto.user.UserLoginDTO;
import com.tuevento.generador.application.dto.user.AuthResponseDTO;
import com.tuevento.generador.application.mapper.UserMapper;
import com.tuevento.generador.domain.model.User;
import com.tuevento.generador.domain.port.out.UserRepositoryPort;
import com.tuevento.generador.application.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case para autenticación de usuarios (login).
 */
@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder    passwordEncoder;
    private final JwtTokenProvider   jwtTokenProvider;

    @Transactional(readOnly = true)
    public AuthResponseDTO execute(UserLoginDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        String token = jwtTokenProvider.generateToken(user.getId());
        return AuthResponseDTO.builder()
            .token(token)
            .user(UserMapper.toResponseDTO(user))
            .build();
    }
}
