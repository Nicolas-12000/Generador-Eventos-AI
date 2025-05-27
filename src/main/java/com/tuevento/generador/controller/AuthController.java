package com.tuevento.generador.controller;

import com.tuevento.generador.application.dto.JwtAuthResponse;
import com.tuevento.generador.application.dto.LoginRequest;
import com.tuevento.generador.application.dto.RegisterRequest;
import com.tuevento.generador.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registra un nuevo usuario.
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequest req
    ) {
        authService.register(req);
        return ResponseEntity.ok().build();
    }

    /**
     * Autentica y devuelve un JWT.
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(
            @Valid @RequestBody LoginRequest req
    ) {
        String token = authService.login(req);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
}