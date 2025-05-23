package com.tuevento.generador.domain.port.out;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.model.LandingPage.PageStatus;
import com.tuevento.generador.domain.model.LandingPage.PageTemplate;

/**
 * Puerto de salida para el repositorio de landing pages
 * Define las operaciones de persistencia para la entidad LandingPage
 */
public interface LandingPageRepositoryPort {
    
    /**
     * Guarda una landing page en el repositorio
     * @param landingPage Landing page a guardar
     * @return Landing page guardada con ID asignado
     */
    LandingPage save(LandingPage landingPage);
    
    /**
     * Busca una landing page por su ID
     * @param id ID de la landing page
     * @return Optional con la landing page encontrada o vacío
     */
    Optional<LandingPage> findById(Long id);
    
    /**
     * Busca una landing page por su slug
     * @param slug Slug único de la landing page
     * @return Optional con la landing page encontrada o vacío
     */
    Optional<LandingPage> findByPageSlug(String slug);
    
    /**
     * Busca una landing page por el ID del evento
     * @param eventId ID del evento asociado
     * @return Optional con la landing page encontrada o vacío
     */
    Optional<LandingPage> findByEventId(Long eventId);
    
    /**
     * Busca landing pages por estado
     * @param status Estado de la página
     * @return Lista de landing pages con el estado especificado
     */
    List<LandingPage> findByStatus(PageStatus status);
    
    /**
     * Busca landing pages por tipo de plantilla
     * @param template Tipo de plantilla
     * @return Lista de landing pages con la plantilla especificada
     */
    List<LandingPage> findByTemplate(PageTemplate template);
    
    /**
     * Busca landing pages por usuario (a través del evento)
     * @param userId ID del usuario propietario
     * @return Lista de landing pages del usuario
     */
    List<LandingPage> findByUserId(UUID userId);
    
    /**
     * Busca landing pages por usuario con paginación
     * @param userId ID del usuario
     * @param page Número de página (0-based)
     * @param size Tamaño de página
     * @return Lista de landing pages paginada
     */
    List<LandingPage> findByUserId(UUID userId, int page, int size);
    
    /**
     * Busca landing pages por título (contiene texto)
     * @param titlePattern Patrón de búsqueda en el título
     * @return Lista de landing pages que coinciden
     */
    List<LandingPage> findByPageTitleContaining(String titlePattern);
    
    /**
     * Busca landing pages por usuario y estado
     * @param userId ID del usuario
     * @param status Estado de la página
     * @return Lista de landing pages filtradas
     */
    List<LandingPage> findByUserIdAndStatus(UUID userId, PageStatus status);
    
    /**
     * Busca landing pages públicas (publicadas)
     * @return Lista de landing pages publicadas
     */
    List<LandingPage> findPublishedPages();
    
    /**
     * Busca landing pages públicas con paginación
     * @param page Número de página (0-based)
     * @param size Tamaño de página
     * @return Lista de landing pages publicadas paginada
     */
    List<LandingPage> findPublishedPages(int page, int size);
    
    /**
     * Busca las landing pages más vistas
     * @param limit Número máximo de resultados
     * @return Lista de landing pages ordenadas por vistas descendente
     */
    List<LandingPage> findTopViewedPages(int limit);
    
    /**
     * Busca landing pages por rango de vistas
     * @param minViews Mínimo número de vistas
     * @param maxViews Máximo número de vistas
     * @return Lista de landing pages en el rango especificado
     */
    List<LandingPage> findByViewCountBetween(int minViews, int maxViews);
    
    /**
     * Verifica si existe una landing page con el slug dado
     * @param slug Slug a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByPageSlug(String slug);
    
    /**
     * Verifica si existe una landing page para el evento dado
     * @param eventId ID del evento
     * @return true si existe, false en caso contrario
     */
    boolean existsByEventId(Long eventId);
    
    /**
     * Cuenta las landing pages de un usuario
     * @param userId ID del usuario
     * @return Número total de landing pages del usuario
     */
    long countByUserId(UUID userId);
    
    /**
     * Cuenta las landing pages por estado
     * @param status Estado de la página
     * @return Número de landing pages con el estado especificado
     */
    long countByStatus(PageStatus status);
    
    /**
     * Obtiene todas las landing pages con paginación
     * @param page Número de página (0-based)
     * @param size Tamaño de página
     * @return Lista de landing pages paginada
     */
    List<LandingPage> findAll(int page, int size);
    
    /**
     * Cuenta el total de landing pages en el sistema
     * @return Número total de landing pages
     */
    long count();
    
    /**
     * Elimina una landing page por su ID
     * @param id ID de la landing page a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    boolean deleteById(Long id);
    
    /**
     * Elimina una landing page por el ID del evento
     * @param eventId ID del evento
     * @return true si se eliminó, false si no se encontró
     */
    boolean deleteByEventId(Long eventId);
    
    /**
     * Actualiza una landing page
     * @param landingPage Landing page con datos actualizados
     * @return Landing page actualizada
     */
    LandingPage update(LandingPage landingPage);
    
    /**
     * Incrementa el contador de vistas de una landing page
     * @param id ID de la landing page
     * @return true si se actualizó, false si no se encontró
     */
    boolean incrementViewCount(Long id);
    
    /**
     * Incrementa el contador de visitantes únicos
     * @param id ID de la landing page
     * @return true si se actualizó, false si no se encontró
     */
    boolean incrementUniqueVisitors(Long id);
}
