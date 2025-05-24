package com.tuevento.generador.application.dto.evento;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear un nuevo evento
 * Incluye validaciones robustas y documentación clara
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoCreateDTO {
    
    @NotBlank(message = "El nombre del evento es obligatorio")
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    private String name;
    
    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    private String description;
    
    @NotNull(message = "La fecha y hora del evento es obligatoria")
    @Future(message = "La fecha del evento debe ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime eventDateTime;
    
    @NotBlank(message = "Los detalles de ubicación son obligatorios")
    @Size(min = 5, max = 500, message = "Los detalles de ubicación deben tener entre 5 y 500 caracteres")
    private String locationDetails;
    
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    private String locationAddress;
    
    @Size(max = 100, message = "El nombre del organizador no puede exceder 100 caracteres")
    private String organizerName;
    
    @Email(message = "El email del organizador debe ser válido")
    private String organizerEmail;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "El teléfono debe ser un número válido")
    private String organizerPhone;
    
    @Size(max = 50, message = "El tipo de evento no puede exceder 50 caracteres")
    private String eventType;
    
    @Min(value = 1, message = "El número máximo de asistentes debe ser al menos 1")
    @Max(value = 100000, message = "El número máximo de asistentes no puede exceder 100,000")
    private Integer maxAttendees;
    
    @Pattern(regexp = "^https?://.*", message = "La URL de tickets debe ser válida")
    private String ticketUrl;
    
    @Pattern(regexp = "^https?://.*", message = "La URL de imagen debe ser válida")
    private String eventImageUrl;
}