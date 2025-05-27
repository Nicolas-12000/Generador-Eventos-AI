// src/main/java/com/tuevento/generador/application/mapper/EventMapper.java
package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.EventDto;
import com.tuevento.generador.domain.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
  componentModel = "spring",
  uses = { ItineraryMapper.class, SpeakerMapper.class, SponsorMapper.class }
)
public interface EventMapper {
    @Mapping(target = "itineraries", source = "itineraries")
    @Mapping(target = "speakers",    source = "speakers")
    @Mapping(target = "sponsors",    source = "sponsors")
    Event toEntity(EventDto dto);

    EventDto toDto(Event entity);
}
