package com.tuevento.generador.infraestructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.model.LandingPage.PageStatus;
import com.tuevento.generador.domain.model.LandingPage.PageTemplate;

/**
 * Repositorio JPA para la entidad LandingPage
 * Extiende JpaRepository para operaciones CRUD básicas
 */
@Repository
public interface LandingPageJpaRepository extends JpaRepository<LandingPage, Long> {
    
    /**
     * Busca una landing page por su slug
     */
    Optional<LandingPage> findByPageSlug(String slug);
    
    /**
     * Busca una landing page por el ID del evento
     */
    Optional<LandingPage> findByEventId(Long eventId);
    
    /**
     * Busca landing pages por estado
     */
    List<LandingPage> findByStatus(PageStatus status);
    
    /**
     * Busca landing pages por estado con paginación
     */
    Page<LandingPage> findByStatus(PageStatus status, Pageable pageable);
    
    /**
     * Busca landing pages por tipo de plantilla
     */
    List<LandingPage> findByTemplate(PageTemplate template);
    
    /**
     * Busca landing pages por tipo de plantilla con paginación
     */
    Page<LandingPage> findByTemplate(PageTemplate template, Pageable pageable);
    
    /**
     * Busca landing pages por usuario (a través del evento)
     */
    @Query("SELECT lp FROM LandingPage lp JOIN lp.event e WHERE e.userId = :userId")
    List<LandingPage> findByUserId(@Param("userId") UUID userId);
    
    /**
     * Busca landing pages por usuario con paginación
     */
    @Query("SELECT lp FROM LandingPage lp JOIN lp.event e WHERE e.userId = :userId")
    Page<LandingPage> findByUserId(@Param("userId") UUID userId, Pageable pageable);
    
    /**
     * Busca landing pages por título (contiene texto)
     */
    List<LandingPage> findByPageTitleContainingIgnoreCase(String titlePattern);
    
    /**
     * Busca landing pages por título con paginación
     */
    Page<LandingPage> findByPageTitleContainingIgnoreCase(String titlePattern, Pageable pageable);
    
    /**
     * Busca landing pages por usuario y estado
     */
    @Query("SELECT lp FROM LandingPage lp JOIN lp.event e WHERE e.userId = :userId AND lp.status = :status")
    List<LandingPage> findByUserIdAndStatus(@Param("userId") UUID userId, @Param("status") PageStatus status);
    
    /**
     * Busca landing pages por usuario y estado con paginación
     */
    @Query("SELECT lp FROM LandingPage lp JOIN lp.event e WHERE e.userId = :userId AND lp.status = :status")
    Page<LandingPage> findByUserIdAndStatus(@Param("userId") UUID userId, @Param("status") PageStatus status, Pageable pageable);
    
    /**
     * Busca landing pages públicas (publicadas)
     */
    List<LandingPage> findByStatusOrderByPublishedAtDesc(PageStatus status);
    
    /**
     * Busca landing pages públicas con paginación
     */
    Page<LandingPage> findByStatusOrderByPublishedAtDesc(PageStatus status, Pageable pageable);
    
    /**
     * Busca las landing pages más vistas
     */
    List<LandingPage> findTopByOrderByViewCountDesc(Pageable pageable);
    
    /**
     * Busca landing pages por rango de vistas
     */
    List<LandingPage> findByViewCountBetween(int minViews, int maxViews);
    
    /**
     * Busca landing pages por rango de vistas con paginación
     */
    Page<LandingPage> findByViewCountBetween(int minViews, int maxViews, Pageable pageable);
    
    /**
     * Verifica si existe una landing page con el slug dado
     */
    boolean existsByPageSlug(String slug);
    
    /**
     * Verifica si existe una landing page para el evento dado
     */
    boolean existsByEventId(Long eventId);
    
    /**
     * Cuenta las landing pages de un usuario
     */
    @Query("SELECT COUNT(lp) FROM LandingPage lp JOIN lp.event e WHERE e.userId = :userId")
    long countByUserId(@Param("userId") UUID userId);
    
    /**
     * Cuenta las landing pages por estado
     */
    long countByStatus(PageStatus status);
    
    /**
     * Elimina una landing page por el ID del evento
     */
    int deleteByEventId(Long eventId);
    
    /**
     * Incrementa el contador de vistas de una landing page
     */
    @Modifying
    @Query("UPDATE LandingPage lp SET lp.viewCount = lp.viewCount + 1, lp.updatedAt = CURRENT_TIMESTAMP WHERE lp.id = :id")
    int incrementViewCount(@Param("id") Long id);
    
    /**
     * Incrementa el contador de visitantes únicos
     */
    @Modifying
    @Query("UPDATE LandingPage lp SET lp.uniqueVisitors = lp.uniqueVisitors + 1, lp.updatedAt = CURRENT_TIMESTAMP WHERE lp.id = :id")
    int incrementUniqueVisitors(@Param("id") Long id);
    
    /**
     * Busca landing pages por múltiples criterios
     */
    @Query("SELECT lp FROM LandingPage lp JOIN lp.event e WHERE " +
           "(:userId IS NULL OR e.userId = :userId) AND " +
           "(:status IS NULL OR lp.status = :status) AND " +
           "(:template IS NULL OR lp.template = :template) AND " +
           "(:searchTerm IS NULL OR LOWER(lp.pageTitle) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<LandingPage> findLandingPagesByCriteria(
            @Param("userId") UUID userId,
            @Param("status") PageStatus status,
            @Param("template") PageTemplate template,
            @Param("searchTerm") String searchTerm,
            Pageable pageable);
    
    /**
     * Busca landing pages populares (con más vistas)
     */
    @Query("SELECT lp FROM LandingPage lp WHERE lp.status = 'PUBLISHED' ORDER BY lp.viewCount DESC, lp.uniqueVisitors DESC")
    List<LandingPage> findPopularPages(Pageable pageable);
    
    /**
     * Busca landing pages recientes (publicadas recientemente)
     */
    @Query("SELECT lp FROM LandingPage lp WHERE lp.status = 'PUBLISHED' AND lp.publishedAt IS NOT NULL ORDER BY lp.publishedAt DESC")
    List<LandingPage> findRecentlyPublishedPages(Pageable pageable);
}