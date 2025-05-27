package com.tuevento.generador.config;

import com.tuevento.generador.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          // deshabilitamos CSRF porque usamos JWT
          .csrf(csrf -> csrf.disable())
          // no usamos sesiones HTTP
          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          // configuramos permisos por ruta
          .authorizeHttpRequests(auth -> auth
              // permitimos acceso sin token a registro y login
              .requestMatchers("/api/auth/**", "/error").permitAll()
              // cualquier otra petición requiere autenticación
              .anyRequest().authenticated()
          )
          // registramos nuestro filtro de JWT antes del filtro de usuario/contraseña
          .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
