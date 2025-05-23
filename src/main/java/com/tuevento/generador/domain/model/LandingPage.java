package com.tuevento.generador.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de dominio LandingPage
 * Representa una página de aterrizaje generada para un evento
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
    @Column(name = "id")
    private Long id;
    
    @Column(name = "event_id", nullable = false, unique = true)
    private Long eventId; // Link to the event
    
    @Column(name = "page_title", nullable = false, length = 200)
    private String pageTitle;
    
    @Column(name = "page_slug", nullable = false, unique = true, length = 250)
    private String pageSlug; // URL-friendly identifier
    
    @Column(name = "main_content_html", columnDefinition = "LONGTEXT")
    private String mainContentHtml; // AI-generated/refined HTML content
    
    @Column(name = "custom_css", columnDefinition = "TEXT")
    private String customCss; // Minimal, specific CSS for this page
    
    @Column(name = "custom_js", columnDefinition = "TEXT")
    private String customJs; // Minimal, specific JS for interactions
    
    @Column(name = "google_maps_query", length = 500)
    private String googleMapsQuery; // To be used by frontend JS Maps API
    
    @Column(name = "google_maps_embed_code", columnDefinition = "TEXT")
    private String googleMapsEmbedCode; // Generated embed code
    
    // Template and styling configuration
    @Column(name = "selected_style", length = 50)
    @Builder.Default
    private String selectedStyle = "default"; // Identifier for base Thymeleaf layout/theme
    
    @Enumerated(EnumType.STRING)
    @Column(name = "template", nullable = false)
    private PageTemplate template; // Template type enum
    
    // Active sections configuration - stored as JSON or comma-separated
    @ElementCollection
    @CollectionTable(name = "landing_page_sections", 
                    joinColumns = @JoinColumn(name = "landing_page_id"))
    @Column(name = "section_name")
    @Builder.Default
    private List<String> activeSections = new ArrayList<>(); // e.g., ["contact_form", "gallery", "agenda"]
    
    // SEO and meta information
    @Column(name = "meta_description", length = 500)
    private String metaDescription;
    
    @Column(name = "meta_keywords", length = 500)
    private String metaKeywords;
    
    @Column(name = "og_image_url")
    private String ogImageUrl;
    
    // Analytics and tracking
    @Column(name = "google_analytics_id", length = 50)
    private String googleAnalyticsId;
    
    @Column(name = "facebook_pixel_id", length = 50)
    private String facebookPixelId;
    
    // Page status and timestamps
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private PageStatus status = PageStatus.DRAFT;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    
    // Performance metrics
    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Integer viewCount = 0;
    
    @Column(name = "unique_visitors", nullable = false)
    @Builder.Default
    private Integer uniqueVisitors = 0;
    
    // Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    private Event event;
    
    /**
     * Factory method para crear una nueva landing page
     */
    public static LandingPage createNew(Long eventId, String pageTitle, PageTemplate template) {
        String slug = generateSlug(pageTitle);
        return LandingPage.builder()
                .eventId(eventId)
                .pageTitle(pageTitle)
                .pageSlug(slug)
                .template(template)
                .selectedStyle("default")
                .status(PageStatus.DRAFT)
                .viewCount(0)
                .uniqueVisitors(0)
                .activeSections(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Business method para actualizar el contenido HTML generado por IA
     */
    public void updateGeneratedContent(String htmlContent, String cssContent, String jsContent) {
        this.mainContentHtml = htmlContent;
        this.customCss = cssContent;
        this.customJs = jsContent;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Business method para configurar Google Maps
     */
    public void configureGoogleMaps(String query, String embedCode) {
        this.googleMapsQuery = query;
        this.googleMapsEmbedCode = embedCode;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Business method para activar/desactivar secciones
     */
    public void toggleSection(String sectionName) {
        if (activeSections.contains(sectionName)) {
            activeSections.remove(sectionName);
        } else {
            activeSections.add(sectionName);
        }
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Business method para publicar la página
     */
    public void publish() {
        if (this.status == PageStatus.DRAFT && isReadyForPublication()) {
            this.status = PageStatus.PUBLISHED;
            this.publishedAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }
    }
    
    /**
     * Business method para verificar si está lista para publicación
     */
    public boolean isReadyForPublication() {
        return pageTitle != null && !pageTitle.trim().isEmpty() &&
               mainContentHtml != null && !mainContentHtml.trim().isEmpty() &&
               eventId != null;
    }
    
    /**
     * Business method para incrementar vistas
     */
    public void incrementView() {
        this.viewCount++;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Helper method para generar slug desde el título
     */
    private static String generateSlug(String title) {
        return title.toLowerCase()
                   .replaceAll("[^a-z0-9\\s-]", "")
                   .replaceAll("\\s+", "-")
                   .replaceAll("-+", "-")
                   .replaceAll("^-|-$", "");
    }
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (pageSlug == null && pageTitle != null) {
            pageSlug = generateSlug(pageTitle);
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
 * Enum para los tipos de plantilla
 */
public enum PageTemplate {
    CONFERENCE("conference", "Plantilla para conferencias y seminarios"),
    WORKSHOP("workshop", "Plantilla para talleres y cursos"),
    CONCERT("concert", "Plantilla para conciertos y eventos musicales"),
    CORPORATE("corporate", "Plantilla para eventos corporativos"),
    WEDDING("wedding", "Plantilla para bodas y celebraciones"),
    SPORTS("sports", "Plantilla para eventos deportivos"),
    FESTIVAL("festival", "Plantilla para festivales y ferias"),
    MINIMAL("minimal", "Plantilla minimalista universal");
    
    private final String code;
    private final String description;
    
    PageTemplate(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() { return code; }
    public String getDescription() { return description; }
}

/**
 * Enum para el estado de la página
 */
public enum PageStatus {
    DRAFT,
    PUBLISHED,
    ARCHIVED,
    MAINTENANCE
}

}