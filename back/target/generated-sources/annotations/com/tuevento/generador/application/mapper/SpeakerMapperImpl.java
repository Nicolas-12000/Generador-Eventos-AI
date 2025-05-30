package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.SpeakerDto;
import com.tuevento.generador.domain.model.Speaker;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-30T15:47:03-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class SpeakerMapperImpl implements SpeakerMapper {

    @Override
    public Speaker toEntity(SpeakerDto dto) {
        if ( dto == null ) {
            return null;
        }

        Speaker.SpeakerBuilder speaker = Speaker.builder();

        speaker.id( dto.getId() );
        speaker.name( dto.getName() );
        speaker.biography( dto.getBiography() );
        speaker.photoUrl( dto.getPhotoUrl() );
        speaker.email( dto.getEmail() );
        speaker.linkedin( dto.getLinkedin() );
        speaker.twitter( dto.getTwitter() );

        return speaker.build();
    }

    @Override
    public SpeakerDto toDto(Speaker entity) {
        if ( entity == null ) {
            return null;
        }

        SpeakerDto speakerDto = new SpeakerDto();

        speakerDto.setId( entity.getId() );
        speakerDto.setName( entity.getName() );
        speakerDto.setBiography( entity.getBiography() );
        speakerDto.setPhotoUrl( entity.getPhotoUrl() );
        speakerDto.setEmail( entity.getEmail() );
        speakerDto.setLinkedin( entity.getLinkedin() );
        speakerDto.setTwitter( entity.getTwitter() );

        return speakerDto;
    }
}
