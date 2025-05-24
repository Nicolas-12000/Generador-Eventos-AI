package com.tuevento.generador.domain.model;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SpeakerInfo {
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
}
