package com.tuevento.generador.service;

import com.tuevento.generador.application.dto.SponsorDto;
import com.tuevento.generador.application.mapper.SponsorMapper;
import com.tuevento.generador.domain.model.Sponsor;
import com.tuevento.generador.repository.SponsorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SponsorService {

    private final SponsorRepository sponsorRepository;
    private final SponsorMapper sponsorMapper;

    public List<SponsorDto> getAllSponsors() {
        return sponsorRepository.findAll()
                .stream()
                .map(sponsorMapper::toDto)
                .collect(Collectors.toList());
    }

    public SponsorDto getSponsorById(UUID id) {
        Sponsor sponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sponsor no encontrado con id: " + id));
        return sponsorMapper.toDto(sponsor);
    }

    public SponsorDto createSponsor(SponsorDto dto) {
        Sponsor sponsor = sponsorMapper.toEntity(dto);
        Sponsor saved = sponsorRepository.save(sponsor);
        return sponsorMapper.toDto(saved);
    }

    public SponsorDto updateSponsor(UUID id, SponsorDto dto) {
        Sponsor existing = sponsorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sponsor no encontrado con id: " + id));

        existing.setName(dto.getName());
        existing.setWebsite(dto.getWebsite());
        // Actualiza otros campos si es necesario

        Sponsor updated = sponsorRepository.save(existing);
        return sponsorMapper.toDto(updated);
    }

    public void deleteSponsor(UUID id) {
        if (!sponsorRepository.existsById(id)) {
            throw new IllegalArgumentException("Sponsor no encontrado con id: " + id);
        }
        sponsorRepository.deleteById(id);
    }
}
