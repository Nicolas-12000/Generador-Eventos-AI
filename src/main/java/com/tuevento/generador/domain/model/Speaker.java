package com.tuevento.generador.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "speakers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;
    @Column(length = 1_000)
    private String biography;
    private String photoUrl;
    private String email;
    private String linkedin;
    private String twitter;

    @ManyToMany(mappedBy = "speakers")
    private Set<Event> events;

    @OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Itinerary> itineraries;
}
