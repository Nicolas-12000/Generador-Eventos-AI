// src/main/java/com/tuevento/generador/domain/model/Event.java
package com.tuevento.generador.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;
    @Column(length = 2_000)
    private String description;
    private LocalDateTime eventDateTime;
    private String locationDetails;
    private String locationAddress;
    private String organizerName;
    private String organizerEmail;
    private String organizerPhone;
    private String eventType;
    private Integer maxAttendees;
    private String ticketUrl;
    private String eventImageUrl;

    /** Costo en tokens por generar la landing */
    private Integer tokenCost;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Itinerary> itineraries;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "event_speakers",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "speaker_id"))
    private Set<Speaker> speakers;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "event_sponsors",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "sponsor_id"))
    private Set<Sponsor> sponsors;
}