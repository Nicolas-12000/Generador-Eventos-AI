package com.tuevento.generador.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * Componente para generar y validar tokens JWT.
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;              // tu secreto en Base64 o texto plano

    @Value("${jwt.expiration-in-ms}")
    private long validityInMs;

    private SecretKey getSigningKey() {
        // Crea un SecretKey HMAC-SHA usando tu cadena de secreto
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un token JWT firmado con HS512 que lleva el userId en el subject.
     */
    public String generateToken(UUID userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
            .claim("sub", userId.toString()) 
            .claim("iat", now) 
            .claim("exp", expiry)
            .signWith(getSigningKey())  
            .compact();
    }

    /**
     * Valida que el token JWT sea correcto y no haya expirado.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extrae el userId del subject del token JWT.
     */
    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

        return UUID.fromString(claims.getSubject());
    }
}