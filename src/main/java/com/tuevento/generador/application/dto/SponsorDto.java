package com.tuevento.generador.application.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class SponsorDto {
    private UUID id;
    private String name;
    private String logoUrl;
    private String website;
    private String sponsorshipLevel;
}
