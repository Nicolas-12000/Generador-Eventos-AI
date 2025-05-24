package com.tuevento.generador.application.mapper;

import com.tuevento.generador.application.dto.landing.LandingPageCreateDTO;
import com.tuevento.generador.application.dto.landing.LandingPageResponseDTO;
import com.tuevento.generador.application.dto.landing.LandingPageUpdateDTO;
import com.tuevento.generador.domain.model.LandingPage;

import java.util.ArrayList;
import java.util.List;

public class LandingPageMapper {

    public static LandingPage toEntity(LandingPageCreateDTO dto) {
        List<String> sections = new ArrayList<>();
        if (dto.isShowMap())      sections.add("map");
        if (dto.isShowAgenda())   sections.add("agenda");
        if (dto.isShowSpeakers()) sections.add("speakers");

        return LandingPage.builder()
                .eventId(dto.getEventId())
                .pageTitle(dto.getCustomTitle())
                .mainContentHtml(dto.getWelcomeMessage())
                .ogImageUrl(dto.getBackgroundImageUrl())
                .selectedStyle(dto.getAccentColor())
                .activeSections(sections)
                .build();
    }

    public static LandingPageResponseDTO toResponseDTO(LandingPage landingPage) {
        List<String> sections = landingPage.getActiveSections();

        return LandingPageResponseDTO.builder()
                .id(landingPage.getId())
                .eventId(landingPage.getEventId())
                .customTitle(landingPage.getPageTitle())
                .welcomeMessage(landingPage.getMainContentHtml())
                .backgroundImageUrl(landingPage.getOgImageUrl())
                .accentColor(landingPage.getSelectedStyle())
                .showMap(sections.contains("map"))
                .showAgenda(sections.contains("agenda"))
                .showSpeakers(sections.contains("speakers"))
                .build();
    }

    public static void updateEntity(LandingPage landingPage, LandingPageUpdateDTO dto) {
        dto.getCustomTitle().ifPresent(landingPage::setPageTitle);
        dto.getWelcomeMessage().ifPresent(landingPage::setMainContentHtml);
        dto.getBackgroundImageUrl().ifPresent(landingPage::setOgImageUrl);
        dto.getAccentColor().ifPresent(landingPage::setSelectedStyle);

        dto.getShowMap().ifPresent(show -> {
            if (show) landingPage.getActiveSections().add("map");
            else       landingPage.getActiveSections().remove("map");
        });
        dto.getShowAgenda().ifPresent(show -> {
            if (show) landingPage.getActiveSections().add("agenda");
            else       landingPage.getActiveSections().remove("agenda");
        });
        dto.getShowSpeakers().ifPresent(show -> {
            if (show) landingPage.getActiveSections().add("speakers");
            else       landingPage.getActiveSections().remove("speakers");
        });
    }
}
