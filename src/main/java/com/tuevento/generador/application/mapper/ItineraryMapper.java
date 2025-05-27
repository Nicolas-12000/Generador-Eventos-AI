package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.ItineraryDto;
import com.tuevento.generador.domain.model.Itinerary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItineraryMapper {
    Itinerary toEntity(ItineraryDto dto);
    ItineraryDto toDto(Itinerary entity);
}