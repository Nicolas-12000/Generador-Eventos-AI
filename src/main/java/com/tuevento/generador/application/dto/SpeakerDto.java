package com.tuevento.generador.application.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class SpeakerDto {
    private UUID id;
    private String name;
    private String biography;
    private String photoUrl;
    private String email;
    private String linkedin;
    private String twitter;
}
