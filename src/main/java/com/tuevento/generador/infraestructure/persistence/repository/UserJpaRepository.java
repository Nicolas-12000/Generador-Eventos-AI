package com.tuevento.generador.infraestructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tuevento.generador.domain.model.User;

/**
 * Repositorio JPA para la entidad User
 * Extiende JpaRepository para operaciones CRUD básicas
 */
@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
    
    /**
     * Busca un usuario por su nombre de usuario
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Busca un usuario por su email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el username dado
     */
    boolean existsByUsername(String username);
    
    /**
     * Verifica si existe un usuario con el email dado
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca todos los usuarios habilitados
     */
    List<User> findByEnabledTrue();
    
    /**
     * Busca usuarios habilitados con paginación
     */
    Page<User> findByEnabledTrue(Pageable pageable);
    
    /**
     * Busca usuarios por nombre o apellido (contiene texto)
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> findBySearchTerm(@Param("search") String searchTerm);
    
    /**
     * Busca usuarios por nombre o apellido con paginación
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<User> findBySearchTerm(@Param("search") String searchTerm, Pageable pageable);
    
    /**
     * Cuenta usuarios habilitados
     */
    long countByEnabledTrue();
}
