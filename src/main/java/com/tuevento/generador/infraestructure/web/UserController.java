package com.tuevento.generador.infraestructure.web;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tuevento.generador.application.dto.user.AuthResponseDTO;
import com.tuevento.generador.application.dto.user.PasswordResetDTO;
import com.tuevento.generador.application.dto.user.UserLoginDTO;
import com.tuevento.generador.application.dto.user.UserRegisterDTO;
import com.tuevento.generador.application.dto.user.UserResponseDTO;
import com.tuevento.generador.application.dto.user.UserUpdateDTO;
import com.tuevento.generador.application.usecase.user.AuthenticateUserUseCase;
import com.tuevento.generador.application.usecase.user.GetUserProfileUseCase;
import com.tuevento.generador.application.usecase.user.RegisterUserUseCase;
import com.tuevento.generador.application.usecase.user.ResetPasswordUseCase;
import com.tuevento.generador.application.usecase.user.UpdateUserProfileUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase      registerUserUseCase;
    private final AuthenticateUserUseCase  authenticateUserUseCase;
    private final GetUserProfileUseCase    getUserProfileUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final ResetPasswordUseCase     resetPasswordUseCase;

    /**
     * POST /api/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @RequestBody @Valid UserRegisterDTO dto
    ) {
        UserResponseDTO created = registerUserUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * POST /api/users/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody @Valid UserLoginDTO dto
    ) {
        AuthResponseDTO auth = authenticateUserUseCase.execute(dto);
        return ResponseEntity.ok(auth);
    }

    /**
     * GET /api/users/me
     * (X-User-Id is extracted by your JWT filter in a real setup; here for now we accept a header)
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(
            @RequestHeader("X-User-Id") UUID userId
    ) {
        UserResponseDTO dto = getUserProfileUseCase.execute(userId);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/users/me
     */
    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateProfile(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody @Valid UserUpdateDTO dto
    ) {
        UserResponseDTO updated = updateUserProfileUseCase.execute(userId, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * POST /api/users/reset-password
     */
    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(
            @RequestBody @Valid PasswordResetDTO dto
    ) {
        resetPasswordUseCase.execute(dto);
    }
}