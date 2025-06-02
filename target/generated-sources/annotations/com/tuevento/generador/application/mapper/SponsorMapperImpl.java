package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.SponsorDto;
import com.tuevento.generador.domain.model.Sponsor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T22:23:06-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
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
        sponsor.logoUrl( dto.getLogoUrl() );
        sponsor.name( dto.getName() );
        sponsor.sponsorshipLevel( dto.getSponsorshipLevel() );
        sponsor.website( dto.getWebsite() );

        return sponsor.build();
    }

    @Override
    public SponsorDto toDto(Sponsor entity) {
        if ( entity == null ) {
            return null;
        }

        SponsorDto sponsorDto = new SponsorDto();

        sponsorDto.setId( entity.getId() );
        sponsorDto.setLogoUrl( entity.getLogoUrl() );
        sponsorDto.setName( entity.getName() );
        sponsorDto.setSponsorshipLevel( entity.getSponsorshipLevel() );
        sponsorDto.setWebsite( entity.getWebsite() );

        return sponsorDto;
    }
}
