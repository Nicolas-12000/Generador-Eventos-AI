package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.ItineraryDto;
import com.tuevento.generador.domain.model.Itinerary;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-30T15:47:03-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ItineraryMapperImpl implements ItineraryMapper {

    @Override
    public Itinerary toEntity(ItineraryDto dto) {
        if ( dto == null ) {
            return null;
        }

        Itinerary.ItineraryBuilder itinerary = Itinerary.builder();

        itinerary.id( dto.getId() );
        itinerary.title( dto.getTitle() );
        itinerary.description( dto.getDescription() );
        itinerary.startTime( dto.getStartTime() );
        itinerary.endTime( dto.getEndTime() );

        return itinerary.build();
    }

    @Override
    public ItineraryDto toDto(Itinerary entity) {
        if ( entity == null ) {
            return null;
        }

        ItineraryDto itineraryDto = new ItineraryDto();

        itineraryDto.setId( entity.getId() );
        itineraryDto.setTitle( entity.getTitle() );
        itineraryDto.setDescription( entity.getDescription() );
        itineraryDto.setStartTime( entity.getStartTime() );
        itineraryDto.setEndTime( entity.getEndTime() );

        return itineraryDto;
    }
}
