package com.tuevento.generador.domain.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "landing_templates")
public class LandingTemplate {
    
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID templateId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String htmlStructure;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String cssStyles;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String jsContent;

    @Column(nullable = false)
    private boolean isActive = true;
}
