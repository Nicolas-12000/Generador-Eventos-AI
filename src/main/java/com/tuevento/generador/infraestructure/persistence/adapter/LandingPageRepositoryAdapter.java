package com.tuevento.generador.infraestructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tuevento.generador.domain.model.LandingPage;
import com.tuevento.generador.domain.model.LandingPage.PageStatus;
import com.tuevento.generador.domain.model.LandingPage.PageTemplate;
import com.tuevento.generador.domain.port.out.LandingPageRepositoryPort;
import com.tuevento.generador.infraestructure.persistence.repository.LandingPageJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador que implementa el puerto LandingPageRepositoryPort
 * Conecta el dominio con la infraestructura de persistencia JPA
 */
@Component
@RequiredArgsConstructor
public class LandingPageRepositoryAdapter implements LandingPageRepositoryPort {
    
    private final LandingPageJpaRepository landingPageJpaRepository;
    
    @Override
    public LandingPage save(LandingPage landingPage) {
        return landingPageJpaRepository.save(landingPage);
    }
    
    @Override
    public Optional<LandingPage> findById(Long id) {
        return landingPageJpaRepository.findById(id);
    }
    
    @Override
    public Optional<LandingPage> findByPageSlug(String slug) {
        return landingPageJpaRepository.findByPageSlug(slug);
    }
    
    @Override
    public Optional<LandingPage> findByEventId(Long eventId) {
        return landingPageJpaRepository.findByEventId(eventId);
    }
    
    @Override
    public List<LandingPage> findByStatus(PageStatus status) {
        return landingPageJpaRepository.findByStatus(status);
    }
    
    @Override
    public List<LandingPage> findByTemplate(PageTemplate template) {
        return landingPageJpaRepository.findByTemplate(template);
    }
    
    @Override
    public List<LandingPage> findByUserId(UUID userId) {
        return landingPageJpaRepository.findByUserId(userId);
    }
    
    @Override
    public List<LandingPage> findByUserId(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return landingPageJpaRepository.findByUserId(userId, pageable).getContent();
    }
    
    @Override
    public List<LandingPage> findByPageTitleContaining(String titlePattern) {
        return landingPageJpaRepository.findByPageTitleContainingIgnoreCase(titlePattern);
    }
    
    @Override
    public List<LandingPage> findByUserIdAndStatus(UUID userId, PageStatus status) {
        return landingPageJpaRepository.findByUserIdAndStatus(userId, status);
    }
    
    @Override
    public List<LandingPage> findPublishedPages() {
        return landingPageJpaRepository.findByStatusOrderByPublishedAtDesc(PageStatus.PUBLISHED);
    }
    
    @Override
    public List<LandingPage> findPublishedPages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return landingPageJpaRepository.findByStatusOrderByPublishedAtDesc(PageStatus.PUBLISHED, pageable).getContent();
    }
    
    @Override
    public List<LandingPage> findTopViewedPages(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return landingPageJpaRepository.findTopByOrderByViewCountDesc(pageable);
    }
    
    @Override
    public List<LandingPage> findByViewCountBetween(int minViews, int maxViews) {
        return landingPageJpaRepository.findByViewCountBetween(minViews, maxViews);
    }
    
    @Override
    public boolean existsByPageSlug(String slug) {
        return landingPageJpaRepository.existsByPageSlug(slug);
    }
    
    @Override
    public boolean existsByEventId(Long eventId) {
        return landingPageJpaRepository.existsByEventId(eventId);
    }
    
    @Override
    public long countByUserId(UUID userId) {
        return landingPageJpaRepository.countByUserId(userId);
    }
    
    @Override
    public long countByStatus(PageStatus status) {
        return landingPageJpaRepository.countByStatus(status);
    }
    
    @Override
    public List<LandingPage> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return landingPageJpaRepository.findAll(pageable).getContent();
    }
    
    @Override
    public long count() {
        return landingPageJpaRepository.count();
    }
    
    @Override
    public boolean deleteById(Long id) {
        if (landingPageJpaRepository.existsById(id)) {
            landingPageJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean deleteByEventId(Long eventId) {
        return landingPageJpaRepository.deleteByEventId(eventId) > 0;
    }
    
    @Override
    public LandingPage update(LandingPage landingPage) {
        return landingPageJpaRepository.save(landingPage);
    }
    
    @Override
    @Transactional
    public boolean incrementViewCount(Long id) {
        return landingPageJpaRepository.incrementViewCount(id) > 0;
    }
    
    @Override
    @Transactional
    public boolean incrementUniqueVisitors(Long id) {
        return landingPageJpaRepository.incrementUniqueVisitors(id) > 0;
    }
    
    /**
     * Métodos adicionales específicos del adaptador
     */
    
    public List<LandingPage> findByStatusWithPagination(PageStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return landingPageJpaRepository.findByStatus(status, pageable).getContent();
    }
    
    public List<LandingPage> findByTemplateWithPagination(PageTemplate template, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return landingPageJpaRepository.findByTemplate(template, pageable).getContent();
    }
    
    public List<LandingPage> findByPageTitleContainingWithPagination(String titlePattern, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return landingPageJpaRepository.findByPageTitleContainingIgnoreCase(titlePattern, pageable).getContent();
    }
    
    public List<LandingPage> findByUserIdAndStatusWithPagination(UUID userId, PageStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return landingPageJpaRepository.findByUserIdAndStatus(userId, status, pageable).getContent();
    }
    
    public List<LandingPage> findByViewCountBetweenWithPagination(int minViews, int maxViews, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return landingPageJpaRepository.findByViewCountBetween(minViews, maxViews, pageable).getContent();
    }
    
    public List<LandingPage> findLandingPagesByCriteria(UUID userId, PageStatus status, PageTemplate template, 
                                                       String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return landingPageJpaRepository.findLandingPagesByCriteria(userId, status, template, searchTerm, pageable).getContent();
    }
    
    public List<LandingPage> findPopularPages(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return landingPageJpaRepository.findPopularPages(pageable);
    }
    
    public List<LandingPage> findRecentlyPublishedPages(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return landingPageJpaRepository.findRecentlyPublishedPages(pageable);
    }
    
    /**
     * Método utilitario para incrementar vistas de forma segura
     */
    @Transactional
    public void recordPageView(Long id, boolean isUniqueVisitor) {
        incrementViewCount(id);
        if (isUniqueVisitor) {
            incrementUniqueVisitors(id);
        }
    }
}
