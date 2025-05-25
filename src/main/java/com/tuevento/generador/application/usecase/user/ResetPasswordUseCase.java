package com.tuevento.generador.application.usecase.user;

import com.tuevento.generador.application.dto.user.PasswordResetDTO;
import com.tuevento.generador.domain.model.User;
import com.tuevento.generador.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ResetPasswordUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder    passwordEncoder;
    // Si tienes un token service, inyectarlo aquí para validar `dto.getResetToken()`

    /**
     * Restablece la contraseña de un usuario que ya validó su token.
     *
     * @param dto  contiene email, resetToken y nueva contraseña
     */
    @Transactional
    public void execute(PasswordResetDTO dto) {
        // 1. Validar el resetToken (omitido: inyectar y usar tu PasswordResetTokenService)
        // passwordResetService.validate(dto.getEmail(), dto.getResetToken());

        // 2. Cargar usuario
        User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado: " + dto.getEmail()));

        // 3. Asignar nueva contraseña
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        // 4. Persistir
        userRepository.save(user);
    }
}
