package com.tuevento.generador.application.dto.evento;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * DTO para actualizar un evento existente
 * Utiliza Optional para campos opcionales y mantiene validaciones
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoUpdateDTO {
    
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    @NotBlank(message = "El nombre del evento es obligatorio")
    @Builder.Default
    private Optional<String> nombre = Optional.empty();
    
    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    @NotBlank(message = "La descripción es obligatoria")
    @Builder.Default
    private Optional<String> descripcion = Optional.empty();
    
    @Future(message = "La fecha del evento debe ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Builder.Default
    @NotNull(message = "La fecha y hora del evento es obligatoria")
    private Optional<LocalDateTime> fechaHora = Optional.empty();
    
    @Size(min = 5, max = 500, message = "Los detalles de ubicación deben tener entre 5 y 500 caracteres")
    @NotBlank(message = "Los detalles de ubicación son obligatorios")
    @Builder.Default
    private Optional<String> detallesUbicacion = Optional.empty();
    
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    @NotBlank(message = "La dirección es obligatoria")
    @Builder.Default
    private Optional<String> direccion = Optional.empty();
    
    @Size(max = 100, message = "El nombre del organizador no puede exceder 100 caracteres")
    @NotBlank(message = "El nombre del organizador es obligatorio")
    @Builder.Default
    private Optional<String> nombreOrganizador = Optional.empty();
    
    @Email(message = "El email del organizador debe ser válido")
    @NotBlank(message = "El email del organizador es obligatorio")
    @Builder.Default
    private Optional<String> emailOrganizador = Optional.empty();
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "El teléfono debe ser un número válido")
    @NotBlank(message = "El teléfono del organizador es obligatorio")
    @Builder.Default
    private Optional<String> telefonoOrganizador = Optional.empty();
    
    @Size(max = 50, message = "El tipo de evento no puede exceder 50 caracteres")
    @NotBlank(message = "El tipo de evento es obligatorio")
    @Builder.Default
    private Optional<String> tipoEvento = Optional.empty();
    
    @Min(value = 1, message = "El número máximo de asistentes debe ser al menos 1")
    @Max(value = 100000, message = "El número máximo de asistentes no puede exceder 100,000")
    @Builder.Default
    private Optional<Integer> maxAsistentes = Optional.empty();
    
    @Pattern(regexp = "^https?://.*", message = "La URL de tickets debe ser válida")
    @NotBlank(message = "La URL de tickets es obligatoria")
    @Builder.Default
    private Optional<String> urlTickets = Optional.empty();
    
    @Pattern(regexp = "^https?://.*", message = "La URL de imagen debe ser válida")
    @NotBlank(message = "La URL de imagen es obligatoria")
    @Builder.Default
    private Optional<String> urlImagen = Optional.empty();
    
    /**
     * Método helper para verificar si el DTO tiene al menos un campo para actualizar
     */
    public boolean hasUpdates() {
        return nombre.isPresent() || descripcion.isPresent() || fechaHora.isPresent() ||
               detallesUbicacion.isPresent() || direccion.isPresent() || 
               nombreOrganizador.isPresent() || emailOrganizador.isPresent() ||
               telefonoOrganizador.isPresent() || tipoEvento.isPresent() ||
               maxAsistentes.isPresent() || urlTickets.isPresent() || urlImagen.isPresent();
    }
}