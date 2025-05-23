package com.tuevento.generador.domain.port.out;

import com.tuevento.generador.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para el repositorio de usuarios
 * Define las operaciones de persistencia para la entidad User
 */
public interface UserRepositoryPort {
    
    /**
     * Guarda un usuario en el repositorio
     * @param user Usuario a guardar
     * @return Usuario guardado con ID asignado
     */
    User save(User user);
    
    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Optional con el usuario encontrado o vacío
     */
    Optional<User> findById(UUID id);
    
    /**
     * Busca un usuario por su nombre de usuario
     * @param username Nombre de usuario
     * @return Optional con el usuario encontrado o vacío
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Busca un usuario por su email
     * @param email Email del usuario
     * @return Optional con el usuario encontrado o vacío
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el username dado
     * @param username Nombre de usuario a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByUsername(String username);
    
    /**
     * Verifica si existe un usuario con el email dado
     * @param email Email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);
    
    /**
     * Obtiene todos los usuarios habilitados
     * @return Lista de usuarios habilitados
     */
    List<User> findAllEnabled();
    
    /**
     * Obtiene todos los usuarios con paginación
     * @param page Número de página (0-based)
     * @param size Tamaño de página
     * @return Lista de usuarios paginada
     */
    List<User> findAll(int page, int size);
    
    /**
     * Cuenta el total de usuarios en el sistema
     * @return Número total de usuarios
     */
    long count();
    
    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    boolean deleteById(UUID id);
    
    /**
     * Actualiza la información de un usuario
     * @param user Usuario con datos actualizados
     * @return Usuario actualizado
     */
    User update(User user);
}