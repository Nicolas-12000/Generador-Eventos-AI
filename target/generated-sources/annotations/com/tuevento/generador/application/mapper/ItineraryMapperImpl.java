package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.ItineraryDto;
import com.tuevento.generador.domain.model.Itinerary;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T22:23:06-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ItineraryMapperImpl implements ItineraryMapper {

    @Override
    public Itinerary toEntity(ItineraryDto dto) {
        if ( dto == null ) {
            return null;
        }

        Itinerary.ItineraryBuilder itinerary = Itinerary.builder();

        itinerary.description( dto.getDescription() );
        itinerary.endTime( dto.getEndTime() );
        itinerary.id( dto.getId() );
        itinerary.startTime( dto.getStartTime() );
        itinerary.title( dto.getTitle() );

        return itinerary.build();
    }

    @Override
    public ItineraryDto toDto(Itinerary entity) {
        if ( entity == null ) {
            return null;
        }

        ItineraryDto itineraryDto = new ItineraryDto();

        itineraryDto.setDescription( entity.getDescription() );
        itineraryDto.setEndTime( entity.getEndTime() );
        itineraryDto.setId( entity.getId() );
        itineraryDto.setStartTime( entity.getStartTime() );
        itineraryDto.setTitle( entity.getTitle() );

        return itineraryDto;
    }
}
