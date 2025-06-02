package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.SponsorDto;
import com.tuevento.generador.domain.model.Sponsor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-30T15:47:03-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class SponsorMapperImpl implements SponsorMapper {

    @Override
    public Sponsor toEntity(SponsorDto dto) {
        if ( dto == null ) {
            return null;
        }

        Sponsor.SponsorBuilder sponsor = Sponsor.builder();

        sponsor.id( dto.getId() );
        sponsor.name( dto.getName() );
        sponsor.logoUrl( dto.getLogoUrl() );
        sponsor.website( dto.getWebsite() );
        sponsor.sponsorshipLevel( dto.getSponsorshipLevel() );

        return sponsor.build();
    }

    @Override
    public SponsorDto toDto(Sponsor entity) {
        if ( entity == null ) {
            return null;
        }

        SponsorDto sponsorDto = new SponsorDto();

        sponsorDto.setId( entity.getId() );
        sponsorDto.setName( entity.getName() );
        sponsorDto.setLogoUrl( entity.getLogoUrl() );
        sponsorDto.setWebsite( entity.getWebsite() );
        sponsorDto.setSponsorshipLevel( entity.getSponsorshipLevel() );

        return sponsorDto;
    }
}
