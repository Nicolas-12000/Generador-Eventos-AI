package com.tuevento.generador.application.dto;

import lombok.Data;

@Data
public class GenerateEventResponse {
    /**
     * El JSON que devuelve Gemini ya parseado en nuestro DTO principal.
     */
    private EventDto eventDto;
}
