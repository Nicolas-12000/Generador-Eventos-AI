package com.tuevento.generador.security;

import com.tuevento.generador.domain.model.AppUser;
import com.tuevento.generador.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Si no tienes roles definidos, devolvemos un usuario con lista vacía de authorities
        Collection<GrantedAuthority> authorities = Collections.emptyList();

        // Puedes mapear roles reales aquí si luego extiendes AppUser con Set<Role>
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            authorities
        );
    }
}
