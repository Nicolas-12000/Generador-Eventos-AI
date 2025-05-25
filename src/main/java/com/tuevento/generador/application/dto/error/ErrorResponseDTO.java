package com.tuevento.generador.application.dto.error;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO genérico para respuestas de error.
 */
@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDTO {
    private String  code;      // Código de error (p.ej. BAD_REQUEST)
    private String  message;   // Mensaje detallado
    private Instant timestamp; // Marca de tiempo
}