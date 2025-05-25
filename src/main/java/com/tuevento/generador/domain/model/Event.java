package com.tuevento.generador.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad de dominio Event
 * Representa un evento creado por un usuario
 */

 
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "event_date_time", nullable = false)
    private LocalDateTime eventDateTime;
    
    @Column(name = "location_details", nullable = false, length = 500)
    private String locationDetails;
    
    @Column(name = "location_address", length = 500)
    private String locationAddress;
    
    @Column(name = "ai_generated_description", columnDefinition = "TEXT")
    private String aiGeneratedDescription;
    
    @Column(name = "ai_generated_keywords", columnDefinition = "TEXT")
    private String aiGeneratedKeywords;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private EventStatus status = EventStatus.DRAFT;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
      name = "event_speakers",
      joinColumns = @JoinColumn(name = "event_id")
    )
    @Builder.Default
    private List<SpeakerInfo> speakers = new ArrayList<>();

     @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
      name = "event_schedule_items",
      joinColumns = @JoinColumn(name = "event_id")
    )
    @Builder.Default
    private List<ScheduleItem> schedule = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UUID userId; // Owning user
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Additional event details
    @Column(name = "organizer_name", length = 100)
    private String organizerName;
    
    @Column(name = "organizer_email")
    private String organizerEmail;
    
    @Column(name = "organizer_phone", length = 20)
    private String organizerPhone;
    
    @Column(name = "event_type", length = 50)
    private String eventType; // Conference, Workshop, Concert, etc.
    
    @Column(name = "max_attendees")
    private Integer maxAttendees;
    
    @Column(name = "ticket_url")
    private String ticketUrl;
    
    @Column(name = "event_image_url")
    private String eventImageUrl;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LandingPage landingPage;

    
    /**
     * Factory method para crear un nuevo evento
     */
    public static Event createNew(User user,
                                  String name,
                                  String description,
                                  LocalDateTime eventDateTime,
                                  String locationDetails) {
        return Event.builder()
                .user(user)
                .name(name)
                .description(description)
                .eventDateTime(eventDateTime)
                .locationDetails(locationDetails)
                .status(EventStatus.DRAFT)
                .build();
    }
    
    /**
     * Business method para actualizar el contenido generado por IA
     */
    public void updateAiGeneratedContent(String aiDescription, String aiKeywords) {
        this.aiGeneratedDescription = aiDescription;
        this.aiGeneratedKeywords = aiKeywords;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Business method para publicar el evento
     */
    public void publish() {
        if (this.status == EventStatus.DRAFT) {
            this.status = EventStatus.PUBLISHED;
            this.updatedAt = LocalDateTime.now();
        }
    }
    
    /**
     * Business method para verificar si el evento es válido para publicación
     */
    public boolean isReadyForPublication() {
        return name != null && !name.trim().isEmpty() &&
               eventDateTime != null && eventDateTime.isAfter(LocalDateTime.now()) &&
               locationDetails != null && !locationDetails.trim().isEmpty() &&
               aiGeneratedDescription != null && !aiGeneratedDescription.trim().isEmpty();
    }
    
    /**
     * Business method para verificar si el usuario es propietario
     */
    public boolean isOwnedBy(UUID userId) {
        return this.userId != null && this.userId.equals(userId);
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
    * Enum para el estado del evento
    */
    public enum EventStatus {
    DRAFT,
    PUBLISHED,
    CANCELLED,
    COMPLETED
}

}

