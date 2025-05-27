package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.SpeakerDto;
import com.tuevento.generador.domain.model.Speaker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeakerMapper {
    Speaker toEntity(SpeakerDto dto);
    SpeakerDto toDto(Speaker entity);
}
