package com.tuevento.generador.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenerateEventRequest {
    @NotBlank
    private String userMessage;
}
