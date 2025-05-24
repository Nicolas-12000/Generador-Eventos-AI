package com.tuevento.generador.application.dto.landing;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleItemDTO {
    private String startTime;   // "HH:mm"
    private String endTime;     // "HH:mm"
    private String description;
}
