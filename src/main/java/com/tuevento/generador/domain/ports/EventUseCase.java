package com.tuevento.generador.domain.ports;

import com.tuevento.generador.domain.model.Events;
import java.util.List;
import java.util.UUID;

public interface EventUseCase {
    Events createEvent(Events event, UUID organizerId);
    Events updateEvent(UUID eventId, Events event);
    void deleteEvent(UUID eventId);
    Events getEventById(UUID eventId);
    List<Events> getEventsByOrganizer(UUID organizerId);
}
