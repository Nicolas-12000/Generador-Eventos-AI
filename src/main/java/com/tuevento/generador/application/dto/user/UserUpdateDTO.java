package com.tuevento.generador.application.dto.user;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.Optional;

/**
 * DTO para actualizar datos de usuario (perfil).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Builder.Default
    private Optional<String> name = Optional.empty();

    @Email(message = "El email debe ser válido")
    @Builder.Default
    private Optional<String> email = Optional.empty();
}
