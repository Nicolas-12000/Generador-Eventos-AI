package com.tuevento.generador.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "events")
public class Events {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID eventId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private String place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User organizer;

    private String category;
    
    private String imageUrl;

    public Events(String name, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, String place,
            User organizer, String category, String imageUrl) {
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.place = place;
        this.organizer = organizer;
        this.category = category;
        this.imageUrl = imageUrl;
    }
    
}

