package com.tuevento.generador.domain.ports;

import com.tuevento.generador.domain.model.Events;
import java.util.List;
import java.util.UUID;

public interface EventUseCase {
    Events createEvent(Events event, UUID userId);
    Events updateEvent(UUID eventId, Events event);
    void deleteEvent(UUID eventId);
    Events getEvent(UUID eventId);
    List<Events> getEventsByUser(UUID userId);
}
