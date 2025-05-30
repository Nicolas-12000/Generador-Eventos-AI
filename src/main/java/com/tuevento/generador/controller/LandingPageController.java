package com.tuevento.generador.controller;


import com.tuevento.generador.repository.LandingPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/landing")
@RequiredArgsConstructor
public class LandingPageController {

    private final LandingPageRepository landingPageRepository;

    /**
     * Obtiene la landing page HTML guardada para un organizador
     * @param organizerName nombre del organizador
     * @return HTML como texto plano con Content-Type text/html
     */
    @GetMapping("/{organizerName}")
    public ResponseEntity<String> getLandingPage(@PathVariable String organizerUsername) {
        return landingPageRepository.findByOrganizerUsername(organizerUsername)
                .map(landingPage -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                        .body(landingPage.getHtmlContent()))
                .orElse(ResponseEntity.notFound().build());
    }
}
