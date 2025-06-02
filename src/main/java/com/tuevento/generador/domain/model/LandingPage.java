package com.tuevento.generador.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandingPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador del organizador, para asociar la landing
    @Column(nullable = false, unique = true)
    private String organizerUsername;

    // Email opcional del organizador
    @Column(nullable = true)
    private String organizerEmail;

    // HTML completo de la landing, almacenado como texto largo
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String htmlContent;

    // Fecha de creación o actualización
    @Column(nullable = false)
    private java.time.LocalDateTime createdAt;

    // Fecha última actualización
    @Column(nullable = false)
    private java.time.LocalDateTime updatedAt;

}
