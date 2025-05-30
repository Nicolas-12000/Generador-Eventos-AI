package com.tuevento.generador.service;

import com.tuevento.generador.application.dto.SpeakerDto;
import com.tuevento.generador.application.mapper.SpeakerMapper;
import com.tuevento.generador.domain.model.Speaker;
import com.tuevento.generador.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeakerService {

    private final SpeakerRepository speakerRepository;
    private final SpeakerMapper speakerMapper;

    // Obtener todos los speakers
    public List<SpeakerDto> getAllSpeakers() {
        return speakerRepository.findAll()
                .stream()
                .map(speakerMapper::toDto)
                .collect(Collectors.toList());
    }

    // Obtener un speaker por id
    public SpeakerDto getSpeakerById(UUID id) {
        Speaker speaker = speakerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Speaker no encontrado con id: " + id));
        return speakerMapper.toDto(speaker);
    }

    // Crear un nuevo speaker
    public SpeakerDto createSpeaker(SpeakerDto dto) {
        Speaker speaker = speakerMapper.toEntity(dto);
        Speaker savedSpeaker = speakerRepository.save(speaker);
        return speakerMapper.toDto(savedSpeaker);
    }

    // Actualizar speaker existente
    public SpeakerDto updateSpeaker(UUID id, SpeakerDto dto) {
        Speaker existing = speakerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Speaker no encontrado con id: " + id));

        // Actualizar campos (puedes ajustar según necesidad)
        existing.setName(dto.getName());
        existing.setBiography(dto.getBiography());
        existing.setPhotoUrl(dto.getPhotoUrl());

        Speaker updated = speakerRepository.save(existing);
        return speakerMapper.toDto(updated);
    }

    // Eliminar speaker
    public void deleteSpeaker(UUID id) {
        if (!speakerRepository.existsById(id)) {
            throw new IllegalArgumentException("Speaker no encontrado con id: " + id);
        }
        speakerRepository.deleteById(id);
    }
}
