package com.tuevento.generador.application.dto.evento;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * DTO para actualizar un evento existente
 * Utiliza Optional para campos opcionales y mantiene validaciones
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoUpdateDTO {

    @NotBlank(message = "El nombre del evento es obligatorio")
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    @Builder.Default
    private Optional<String> name = Optional.empty();

    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    @Builder.Default
    private Optional<String> description = Optional.empty();

    @NotNull(message = "La fecha y hora del evento es obligatoria")
    @Future(message = "La fecha del evento debe ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Builder.Default
    private Optional<LocalDateTime> eventDateTime = Optional.empty();

    @NotBlank(message = "Los detalles de ubicación son obligatorios")
    @Size(min = 5, max = 500, message = "Los detalles de ubicación deben tener entre 5 y 500 caracteres")
    @Builder.Default
    private Optional<String> locationDetails = Optional.empty();

    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    @Builder.Default
    private Optional<String> locationAddress = Optional.empty();

    @Size(max = 100, message = "El nombre del organizador no puede exceder 100 caracteres")
    @Builder.Default
    private Optional<String> organizerName = Optional.empty();

    @Email(message = "El email del organizador debe ser válido")
    @Builder.Default
    private Optional<String> organizerEmail = Optional.empty();

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "El teléfono debe ser un número válido")
    @Builder.Default
    private Optional<String> organizerPhone = Optional.empty();

    @Size(max = 50, message = "El tipo de evento no puede exceder 50 caracteres")
    @Builder.Default
    private Optional<String> eventType = Optional.empty();

    @Min(value = 1, message = "El número máximo de asistentes debe ser al menos 1")
    @Max(value = 100000, message = "El número máximo de asistentes no puede exceder 100,000")
    @Builder.Default
    private Optional<Integer> maxAttendees = Optional.empty();

    @Pattern(regexp = "^https?://.*", message = "La URL de tickets debe ser válida")
    @Builder.Default
    private Optional<String> ticketUrl = Optional.empty();

    @NotBlank(message = "La URL de imagen es obligatoria")
    @Builder.Default
    private Optional<String> eventImageUrl = Optional.empty();

    /**
     * Método helper para verificar si el DTO tiene al menos un campo para actualizar
     */
    public boolean hasUpdates() {
        return name.isPresent()
                || description.isPresent()
                || eventDateTime.isPresent()
                || locationDetails.isPresent()
                || locationAddress.isPresent()
                || organizerName.isPresent()
                || organizerEmail.isPresent()
                || organizerPhone.isPresent()
                || eventType.isPresent()
                || maxAttendees.isPresent()
                || ticketUrl.isPresent()
                || eventImageUrl.isPresent();
    }
}
