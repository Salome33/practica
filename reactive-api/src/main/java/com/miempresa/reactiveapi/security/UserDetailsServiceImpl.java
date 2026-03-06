package com.miempresa.reactiveapi.security;

import com.miempresa.reactiveapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(List.of(
                                new SimpleGrantedAuthority("ROLE_" + user.getRole())
                        ))
                        .accountExpired(false)
                        .accountLocked(!user.getEnabled())
                        .credentialsExpired(false)
                        .disabled(!user.getEnabled())
                        .build()
                )
                .cast(UserDetails.class)
                .switchIfEmpty(
                        Mono.error(new UsernameNotFoundException(
                                "Usuario no encontrado: " + username
                        ))
                );
    }
}