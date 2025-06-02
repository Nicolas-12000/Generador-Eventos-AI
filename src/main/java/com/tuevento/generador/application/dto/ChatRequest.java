package com.tuevento.generador.application.dto;
import lombok.Data;

@Data
public class ChatRequest {
    private String message;
    private String organizerName;
    private String organizerEmail;
}