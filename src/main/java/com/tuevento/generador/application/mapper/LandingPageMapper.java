package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.landing.*;
import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.model.ScheduleItem;
import com.tuevento.generador.domain.model.SpeakerInfo;
import com.tuevento.generador.domain.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class LandingPageMapper {
    private LandingPageMapper() {}

    /**
     * Maps CreateDTO + Event → new LandingPage entity.
     * We attach the Event (so JPA knows the FK), and copy flags → activeSections.
     */
    public static LandingPage toEntity(LandingPageCreateDTO dto, Event event) {
        LandingPage lp = new LandingPage();
        lp.setEvent(event);                                         // set the entity relationship
        lp.setCustomTitle(dto.getCustomTitle());
        lp.setWelcomeMessage(dto.getWelcomeMessage());
        lp.setBackgroundImageUrl(dto.getBackgroundImageUrl());
        lp.setAccentColor(dto.getAccentColor());
        lp.setLocationAddress(dto.getLocationAddress());          // copy over address
        lp.setLatitude(dto.getLatitude());                        // may be null
        lp.setLongitude(dto.getLongitude());

        // build activeSections from flags
        List<String> sections = new ArrayList<>();
        if (dto.isShowMap())      sections.add("map");
        if (dto.isShowSchedule()) sections.add("schedule");
        if (dto.isShowSpeakers()) sections.add("speakers");
        lp.setActiveSections(sections);

        return lp;
    }

    /**
     * Applies an UpdateDTO onto an existing entity.
     * Only those fields present in the Optional will be changed.
     */
    public static void updateEntity(LandingPage entity, LandingPageUpdateDTO dto) {
        dto.getCustomTitle()
           .ifPresent(entity::setCustomTitle);

        dto.getWelcomeMessage()
           .ifPresent(entity::setWelcomeMessage);

        dto.getBackgroundImageUrl()
           .ifPresent(entity::setBackgroundImageUrl);

        dto.getAccentColor()
           .ifPresent(entity::setAccentColor);

        // Rebuild activeSections if any flag is present
        if (dto.getShowMap().isPresent()
         || dto.getShowSchedule().isPresent()
         || dto.getShowSpeakers().isPresent()) {
            entity.getActiveSections().clear();
            dto.getShowMap()
               .filter(b -> b)
               .ifPresent(b -> entity.getActiveSections().add("map"));
            dto.getShowSchedule()
               .filter(b -> b)
               .ifPresent(b -> entity.getActiveSections().add("schedule"));
            dto.getShowSpeakers()
               .filter(b -> b)
               .ifPresent(b -> entity.getActiveSections().add("speakers"));
        }
    }

    /**
     * Maps entity → ResponseDTO
     */
    public static LandingPageResponseDTO toResponseDTO(LandingPage entity) {
        Event event = entity.getEvent();

        List<SpeakerInfoDTO> speakers =
            event.getSpeakers().stream()
                .map(LandingPageMapper::mapSpeaker)
                .collect(Collectors.toList());

        List<ScheduleItemDTO> schedule =
            event.getSchedule().stream()
                .map(LandingPageMapper::mapScheduleItem)
                .collect(Collectors.toList());

        return LandingPageResponseDTO.builder()
            .id(entity.getId())
            .eventId(event.getId())
            .customTitle(entity.getCustomTitle())
            .welcomeMessage(entity.getWelcomeMessage())
            .backgroundImageUrl(entity.getBackgroundImageUrl())
            .accentColor(entity.getAccentColor())
            .showMap(entity.isShowMap())
            .showSchedule(entity.isShowSchedule())
            .showSpeakers(entity.isShowSpeakers())
            .locationAddress(event.getLocationAddress())
            .latitude(entity.getLatitude())
            .longitude(entity.getLongitude())
            .speakers(speakers)
            .schedule(schedule)
            .build();
    }

    private static SpeakerInfoDTO mapSpeaker(SpeakerInfo s) {
        return SpeakerInfoDTO.builder()
            .name(s.getName())
            .description(s.getDescription())
            .startTime(s.getStartTime().toString())
            .endTime(s.getEndTime().toString())
            .build();
    }

    private static ScheduleItemDTO mapScheduleItem(ScheduleItem si) {
        return ScheduleItemDTO.builder()
            .startTime(si.getStartTime().toString())
            .endTime(si.getEndTime().toString())
            .description(si.getDescription())
            .build();
    }
}
