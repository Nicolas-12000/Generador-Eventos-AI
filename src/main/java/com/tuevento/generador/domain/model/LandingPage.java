package com.tuevento.generador.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio LandingPage
 * Representa la página de aterrizaje de un evento simplificada para renderizado.
 */
@Entity
@Table(name = "landing_pages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandingPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    // Título mostrado en la landing
    @Column(name = "page_title", nullable = false, length = 200)
    private String customTitle;

    // Mensaje de bienvenida o introducción
    @Column(name = "welcome_message", length = 500)
    private String welcomeMessage;

    // URL de imagen de fondo (opcional)
    @Column(name = "background_image_url", length = 500)
    private String backgroundImageUrl;

    // Color de acento para la landing (hex)
    @Column(name = "accent_color", length = 7)
    private String accentColor;

    // Coordenadas para el mapa
    @Column(name = "latitude", precision = 10, scale = 6)
    private Double latitude;

    @Column(name = "longitude", precision = 10, scale = 6)
    private Double longitude;

    // Secciones activas configuradas
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "landing_page_sections",
        joinColumns = @JoinColumn(name = "landing_page_id")
    )
    @Column(name = "section_name")
    @Builder.Default
    private List<String> activeSections = new ArrayList<>();

    // Timestamps
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "locationAddress", nullable = false)
    private String locationAddress;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Convenience: show map if section configured
     */
    public boolean isShowMap() {
        return activeSections.contains("map");
    }

    /**
     * Convenience: show schedule if section configured
     */
    public boolean isShowSchedule() {
        return activeSections.contains("schedule");
    }

    /**
     * Convenience: show speakers if section configured
     */
    public boolean isShowSpeakers() {
        return activeSections.contains("speakers");
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
