package com.tuevento.generador.application.dto.user;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;
}