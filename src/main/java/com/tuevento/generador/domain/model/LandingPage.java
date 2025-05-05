package com.tuevento.generador.domain.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

@Entity
@Data
@NoArgsConstructor
@Table(name = "landing_pages")
public class LandingPage {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID landingId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String htmlContent;

    @Column(nullable = false, unique = true)
    private String publicUrl;

    @OneToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Events event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private LandingTemplate template;

    public LandingPage(String htmlContent, String publicUrl, Events event) {
        this.htmlContent = htmlContent;
        this.publicUrl = publicUrl;
        this.event = event;
    }


    
}
