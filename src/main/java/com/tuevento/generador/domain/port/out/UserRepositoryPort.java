package com.tuevento.generador.domain.port.out;

import java.util.Optional;
import com.tuevento.generador.domain.model.User;


/**
 * Puerto de salida para operaciones de persistencia de User
 * Define el contrato que debe implementar el adaptador de persistencia
 */
public interface UserRepositoryPort {
    
    /**
     * Guarda un usuario
     */
    User save(User user);
    
    /**
     * Busca un usuario por ID
     */
    Optional<User> findById(Long id);
    
    /**
     * Busca un usuario por username
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Busca un usuario por email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Verifica si existe un username
     */
    boolean existsByUsername(String username);
    
    /**
     * Verifica si existe un email
     */
    boolean existsByEmail(String email);
    
    /**
     * Elimina un usuario por ID
     */
    void deleteById(Long id);
}