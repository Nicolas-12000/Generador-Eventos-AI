package com.tuevento.generador.application.dto.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que retorna el token JWT y los datos del usuario autenticado.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String           token;
    private UserResponseDTO  user;
}