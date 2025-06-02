package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.SpeakerDto;
import com.tuevento.generador.domain.model.Speaker;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T22:23:06-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class SpeakerMapperImpl implements SpeakerMapper {

    @Override
    public Speaker toEntity(SpeakerDto dto) {
        if ( dto == null ) {
            return null;
        }

        Speaker.SpeakerBuilder speaker = Speaker.builder();

        speaker.biography( dto.getBiography() );
        speaker.email( dto.getEmail() );
        speaker.id( dto.getId() );
        speaker.linkedin( dto.getLinkedin() );
        speaker.name( dto.getName() );
        speaker.photoUrl( dto.getPhotoUrl() );
        speaker.twitter( dto.getTwitter() );

        return speaker.build();
    }

    @Override
    public SpeakerDto toDto(Speaker entity) {
        if ( entity == null ) {
            return null;
        }

        SpeakerDto speakerDto = new SpeakerDto();

        speakerDto.setBiography( entity.getBiography() );
        speakerDto.setEmail( entity.getEmail() );
        speakerDto.setId( entity.getId() );
        speakerDto.setLinkedin( entity.getLinkedin() );
        speakerDto.setName( entity.getName() );
        speakerDto.setPhotoUrl( entity.getPhotoUrl() );
        speakerDto.setTwitter( entity.getTwitter() );

        return speakerDto;
    }
}
