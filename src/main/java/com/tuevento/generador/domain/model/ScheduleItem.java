package com.tuevento.generador.domain.model;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ScheduleItem {
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
}
