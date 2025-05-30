package com.tuevento.generador.service;

import com.tuevento.generador.application.dto.ItineraryDto;
import com.tuevento.generador.application.mapper.ItineraryMapper;
import com.tuevento.generador.domain.model.Itinerary;
import com.tuevento.generador.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final ItineraryMapper itineraryMapper;

    public List<ItineraryDto> getAllItineraries() {
        return itineraryRepository.findAll()
                .stream()
                .map(itineraryMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItineraryDto getItineraryById(UUID id) {
        Itinerary itinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Itinerary no encontrado con id: " + id));
        return itineraryMapper.toDto(itinerary);
    }

    public ItineraryDto createItinerary(ItineraryDto dto) {
        Itinerary itinerary = itineraryMapper.toEntity(dto);
        Itinerary saved = itineraryRepository.save(itinerary);
        return itineraryMapper.toDto(saved);
    }

    public ItineraryDto updateItinerary(UUID id, ItineraryDto dto) {
        Itinerary existing = itineraryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Itinerary no encontrado con id: " + id));

        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setDescription(dto.getDescription());
        // Actualiza otros campos si es necesario

        Itinerary updated = itineraryRepository.save(existing);
        return itineraryMapper.toDto(updated);
    }

    public void deleteItinerary(UUID id) {
        if (!itineraryRepository.existsById(id)) {
            throw new IllegalArgumentException("Itinerary no encontrado con id: " + id);
        }
        itineraryRepository.deleteById(id);
    }
}
