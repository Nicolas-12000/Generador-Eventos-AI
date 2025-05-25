package com.tuevento.generador.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para restablecer contraseña de usuario.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDTO {

    @Email(message = "El email debe ser válido")
    @NotEmpty(message = "El email es obligatorio para restablecer contraseña")
    private String email;

    @NotEmpty(message = "El token de restablecimiento es obligatorio")
    private String resetToken;

    @NotEmpty(message = "La nueva contraseña es obligatoria")
    @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres")
    private String newPassword;
}