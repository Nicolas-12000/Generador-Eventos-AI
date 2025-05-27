package com.tuevento.generador.service;

import com.tuevento.generador.application.dto.LoginRequest;
import com.tuevento.generador.application.dto.RegisterRequest;
import com.tuevento.generador.domain.model.AppUser;
import com.tuevento.generador.exception.UsernameAlreadyExistsException;
import com.tuevento.generador.repository.AppUserRepository;
import com.tuevento.generador.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    /**
     * Registra un nuevo usuario.  
     * Lanza IllegalArgumentException si el username ya existe.
     */
    public void register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new UsernameAlreadyExistsException(req.getUsername());
        }
        AppUser user = AppUser.builder()
            .username(req.getUsername())
            .password(passwordEncoder.encode(req.getPassword()))
            .email(req.getEmail())
            .firstName(req.getFirstName())
            .lastName(req.getLastName())
            .build();

        userRepo.save(user);
    }

    /**
     * Autentica las credenciales y devuelve un JWT.  
     * Lanza AuthenticationException si falla la autenticación.
     */
    public String login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                req.getUsername(),
                req.getPassword()
            )
        );
        return tokenProvider.generateToken(auth);
    }
}
