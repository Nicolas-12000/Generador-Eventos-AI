package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.SponsorDto;
import com.tuevento.generador.domain.model.Sponsor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SponsorMapper {
    Sponsor toEntity(SponsorDto dto);
    SponsorDto toDto(Sponsor entity);
}
