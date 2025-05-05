package com.tuevento.generador.domain.ports;

import com.tuevento.generador.domain.model.Events;
import java.util.UUID;

public interface LandingGenerationUseCase {
    String generateLandingContent(Events event, UUID templateId);
    String previewLanding(UUID eventId, UUID templateId);
    void publishLanding(UUID eventId);
}
