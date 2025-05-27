package com.tuevento.generador.application.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class EventDto {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime eventDateTime;
    private String locationDetails;
    private String locationAddress;
    private String organizerName;
    private String organizerEmail;
    private String organizerPhone;
    private String eventType;
    private Integer maxAttendees;
    private String ticketUrl;
    private String eventImageUrl;
    private Integer tokenCost;

    private List<ItineraryDto> itineraries;
    private List<SpeakerDto> speakers;
    private List<SponsorDto> sponsors;
}
