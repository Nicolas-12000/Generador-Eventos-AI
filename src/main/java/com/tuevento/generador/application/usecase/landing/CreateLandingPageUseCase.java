package com.tuevento.generador.application.usecase.landing;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuevento.generador.application.dto.landing.LandingPageCreateDTO;
import com.tuevento.generador.application.dto.landing.LandingPageResponseDTO;
import com.tuevento.generador.application.mapper.LandingPageMapper;
import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.port.out.EventRepositoryPort;
import com.tuevento.generador.domain.port.out.LandingPageRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateLandingPageUseCase {

    private final EventRepositoryPort         eventRepository;
    private final LandingPageRepositoryPort   repository; 

    /**
     * Crea una LandingPage asociada a un evento.
     *
     * @param createDTO       datos de creación
     * @param currentUserId   id del usuario autenticado
     * @return                DTO con la landing creada
     */
    @Transactional
    public LandingPageResponseDTO execute(LandingPageCreateDTO createDTO, UUID currentUserId) {
        // 1. eventId es obligatorio
        if (createDTO.getEventId() == null) {
            throw new IllegalArgumentException("El campo eventId es obligatorio.");
        }

        // 2. buscar el evento usando la instancia inyectada (no método estático)
        Event event = eventRepository.findById(createDTO.getEventId())
            .orElseThrow(() -> new NoSuchElementException(
                "Evento no encontrado: " + createDTO.getEventId()
            ));

        // 3. autorización: solo el dueño puede crear la landing
        if (!event.getUserId().equals(currentUserId)) {
            throw new SecurityException(
                "No autorizado para crear landing en este evento."
            );
        }

    // 4. Validaciones de campos
    String title = createDTO.getCustomTitle();
if (title == null || title.isBlank()) {
    throw new IllegalArgumentException("El título personalizado es obligatorio.");
}
if (title.length() < 3 || title.length() > 100) {
    throw new IllegalArgumentException("El título debe tener entre 3 y 100 caracteres.");
}

String welcome = createDTO.getWelcomeMessage();
if (welcome != null && welcome.length() > 500) {
    throw new IllegalArgumentException("El mensaje de bienvenida no puede exceder 500 caracteres.");
}

String bgUrl = createDTO.getBackgroundImageUrl();
if (bgUrl != null && !bgUrl.matches("^https?://.*")) {
    throw new IllegalArgumentException("La URL de imagen de fondo debe ser válida.");
}

String color = createDTO.getAccentColor();
if (color != null && !color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
    throw new IllegalArgumentException("El color de acento debe ser un código hexadecimal válido.");
}


    // 5. Reglas de negocio
    if (createDTO.isShowMap()
        && (event.getLocationAddress() == null || event.getLocationAddress().isBlank())) {
        throw new IllegalStateException("No se puede mostrar el mapa sin dirección de evento.");
    }

    if (createDTO.isShowSpeakers() && event.getSpeakers().isEmpty()) {
        throw new IllegalStateException("No se puede mostrar speakers sin oradores asociados.");
    }

    if (createDTO.isShowSchedule() && event.getSchedule().isEmpty()) {
        throw new IllegalStateException("No se puede mostrar agendas asociadas.");
    }
    // 6. map, salvar y devolver DTO
    LandingPage entity = LandingPageMapper.toEntity(createDTO, event);
    LandingPage saved  = repository.save(entity);
    return LandingPageMapper.toResponseDTO(saved);
}

}