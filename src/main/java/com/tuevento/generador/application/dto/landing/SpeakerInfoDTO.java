package com.tuevento.generador.application.dto.landing;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO que describe un orador en la landing page.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeakerInfoDTO {

    @NotBlank(message = "El nombre del orador es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;

    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "El formato de hora debe ser HH:mm")
    private String startTime; // "HH:mm"

    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "El formato de hora debe ser HH:mm")
    private String endTime;   // "HH:mm"
}
