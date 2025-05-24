// src/main/java/com/tuevento/generador/application/dto/landing/SpeakerInfoDTO.java
package com.tuevento.generador.application.dto.landing;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO representing speaker information (no separate entity needed).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeakerInfoDTO {
    private String name;
    private String description;
    private String startTime; // "HH:mm"
    private String endTime;   // "HH:mm"
}
