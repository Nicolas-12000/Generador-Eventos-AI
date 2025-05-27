package com.tuevento.generador.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sponsors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;
    private String logoUrl;
    private String website;
    private String sponsorshipLevel;

    @ManyToMany(mappedBy = "sponsors")
    private Set<Event> events;
}
