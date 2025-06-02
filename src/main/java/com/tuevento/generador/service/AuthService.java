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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
     * Autentica usando email y contraseña, devolviendo un JWT.
     */
    public String login(LoginRequest req) {
        AppUser user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + req.getEmail()));

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), // Spring Security requiere el username
                        req.getPassword()
                )
        );

        return tokenProvider.generateToken(auth);
    }
}
