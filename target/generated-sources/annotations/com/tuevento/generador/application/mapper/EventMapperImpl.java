package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.EventDto;
import com.tuevento.generador.application.dto.ItineraryDto;
import com.tuevento.generador.application.dto.SpeakerDto;
import com.tuevento.generador.application.dto.SponsorDto;
import com.tuevento.generador.domain.model.Event;
import com.tuevento.generador.domain.model.Itinerary;
import com.tuevento.generador.domain.model.Speaker;
import com.tuevento.generador.domain.model.Sponsor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T22:23:06-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Autowired
    private ItineraryMapper itineraryMapper;
    @Autowired
    private SpeakerMapper speakerMapper;
    @Autowired
    private SponsorMapper sponsorMapper;

    @Override
    public Event toEntity(EventDto dto) {
        if ( dto == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.itineraries( itineraryDtoListToItinerarySet( dto.getItineraries() ) );
        event.speakers( speakerDtoListToSpeakerSet( dto.getSpeakers() ) );
        event.sponsors( sponsorDtoListToSponsorSet( dto.getSponsors() ) );
        event.description( dto.getDescription() );
        event.eventDateTime( dto.getEventDateTime() );
        event.eventImageUrl( dto.getEventImageUrl() );
        event.eventType( dto.getEventType() );
        event.id( dto.getId() );
        event.locationAddress( dto.getLocationAddress() );
        event.locationDetails( dto.getLocationDetails() );
        event.maxAttendees( dto.getMaxAttendees() );
        event.name( dto.getName() );
        event.organizerEmail( dto.getOrganizerEmail() );
        event.organizerName( dto.getOrganizerName() );
        event.organizerPhone( dto.getOrganizerPhone() );
        event.ticketUrl( dto.getTicketUrl() );
        event.tokenCost( dto.getTokenCost() );

        return event.build();
    }

    @Override
    public EventDto toDto(Event entity) {
        if ( entity == null ) {
            return null;
        }

        EventDto eventDto = new EventDto();

        eventDto.setDescription( entity.getDescription() );
        eventDto.setEventDateTime( entity.getEventDateTime() );
        eventDto.setEventImageUrl( entity.getEventImageUrl() );
        eventDto.setEventType( entity.getEventType() );
        eventDto.setId( entity.getId() );
        eventDto.setItineraries( itinerarySetToItineraryDtoList( entity.getItineraries() ) );
        eventDto.setLocationAddress( entity.getLocationAddress() );
        eventDto.setLocationDetails( entity.getLocationDetails() );
        eventDto.setMaxAttendees( entity.getMaxAttendees() );
        eventDto.setName( entity.getName() );
        eventDto.setOrganizerEmail( entity.getOrganizerEmail() );
        eventDto.setOrganizerName( entity.getOrganizerName() );
        eventDto.setOrganizerPhone( entity.getOrganizerPhone() );
        eventDto.setSpeakers( speakerSetToSpeakerDtoList( entity.getSpeakers() ) );
        eventDto.setSponsors( sponsorSetToSponsorDtoList( entity.getSponsors() ) );
        eventDto.setTicketUrl( entity.getTicketUrl() );
        eventDto.setTokenCost( entity.getTokenCost() );

        return eventDto;
    }

    protected Set<Itinerary> itineraryDtoListToItinerarySet(List<ItineraryDto> list) {
        if ( list == null ) {
            return null;
        }

        Set<Itinerary> set = new LinkedHashSet<Itinerary>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( ItineraryDto itineraryDto : list ) {
            set.add( itineraryMapper.toEntity( itineraryDto ) );
        }

        return set;
    }

    protected Set<Speaker> speakerDtoListToSpeakerSet(List<SpeakerDto> list) {
        if ( list == null ) {
            return null;
        }

        Set<Speaker> set = new LinkedHashSet<Speaker>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( SpeakerDto speakerDto : list ) {
            set.add( speakerMapper.toEntity( speakerDto ) );
        }

        return set;
    }

    protected Set<Sponsor> sponsorDtoListToSponsorSet(List<SponsorDto> list) {
        if ( list == null ) {
            return null;
        }

        Set<Sponsor> set = new LinkedHashSet<Sponsor>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( SponsorDto sponsorDto : list ) {
            set.add( sponsorMapper.toEntity( sponsorDto ) );
        }

        return set;
    }

    protected List<ItineraryDto> itinerarySetToItineraryDtoList(Set<Itinerary> set) {
        if ( set == null ) {
            return null;
        }

        List<ItineraryDto> list = new ArrayList<ItineraryDto>( set.size() );
        for ( Itinerary itinerary : set ) {
            list.add( itineraryMapper.toDto( itinerary ) );
        }

        return list;
    }

    protected List<SpeakerDto> speakerSetToSpeakerDtoList(Set<Speaker> set) {
        if ( set == null ) {
            return null;
        }

        List<SpeakerDto> list = new ArrayList<SpeakerDto>( set.size() );
        for ( Speaker speaker : set ) {
            list.add( speakerMapper.toDto( speaker ) );
        }

        return list;
    }

    protected List<SponsorDto> sponsorSetToSponsorDtoList(Set<Sponsor> set) {
        if ( set == null ) {
            return null;
        }

        List<SponsorDto> list = new ArrayList<SponsorDto>( set.size() );
        for ( Sponsor sponsor : set ) {
            list.add( sponsorMapper.toDto( sponsor ) );
        }

        return list;
    }
}
