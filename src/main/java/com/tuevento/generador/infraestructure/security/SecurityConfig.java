package com.tuevento.generador.infraestructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // Importante para CSRF
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tuevento.generador.application.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    // El bean jwtAuthenticationFilter sigue igual, pero asegúrate de que esté
    // correctamente definido y que JwtAuthenticationFilter esté actualizado si es necesario.
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF usando la nueva API con lambda
                .csrf(AbstractHttpConfigurer::disable)
                // Configurar la gestión de sesiones para que sea STATELESS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configurar la autorización de solicitudes HTTP
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/users/reset-password",
                                "/landing/**" // vistas públicas
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // Añadir el filtro JWT antes del filtro de autenticación de usuario y contraseña
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}