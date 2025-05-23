package com.tuevento.generador.infraestructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.tuevento.generador.domain.model.User;
import com.tuevento.generador.domain.port.out.UserRepositoryPort;
import com.tuevento.generador.infraestructure.persistence.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Adaptador que implementa el puerto UserRepositoryPort
 * Conecta el dominio con la infraestructura de persistencia JPA
 */
@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    
    private final UserJpaRepository userJpaRepository;
    
    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
    
    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
    
    @Override
    public List<User> findAllEnabled() {
        return userJpaRepository.findByEnabledTrue();
    }
    
    @Override
    public List<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userJpaRepository.findAll(pageable).getContent();
    }
    
    @Override
    public long count() {
        return userJpaRepository.count();
    }
    
    @Override
    public boolean deleteById(UUID id) {
        if (userJpaRepository.existsById(id)) {
            userJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    public User update(User user) {
        return userJpaRepository.save(user);
    }
    
    /**
     * Métodos adicionales específicos del adaptador
     */
    
    public List<User> findBySearchTerm(String searchTerm) {
        return userJpaRepository.findBySearchTerm(searchTerm);
    }
    
    public List<User> findBySearchTerm(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userJpaRepository.findBySearchTerm(searchTerm, pageable).getContent();
    }
    
    public long countEnabled() {
        return userJpaRepository.countByEnabledTrue();
    }
    
    public List<User> findEnabledWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userJpaRepository.findByEnabledTrue(pageable).getContent();
    }
}
