package com.miempresa.reactiveapi.service;

import com.miempresa.reactiveapi.dto.*;
import com.miempresa.reactiveapi.model.User;
import com.miempresa.reactiveapi.repository.UserRepository;
import com.miempresa.reactiveapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Mono<AuthResponse> login(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> AuthResponse.builder()
                        .token(jwtUtil.generateToken(user.getUsername(), user.getRole()))
                        .username(user.getUsername())
                        .role(user.getRole())
                        .expiresIn(jwtUtil.getExpiration())
                        .build())
                .switchIfEmpty(Mono.error(new RuntimeException("Credenciales inválidas")));
    }

    public Mono<AuthResponse> register(RegisterRequest request) {
        return userRepository.existsByUsername(request.getUsername())
                .flatMap(exists -> {
                    if (exists) return Mono.error(new RuntimeException("Username ya existe"));
                    return userRepository.existsByEmail(request.getEmail());
                })
                .flatMap(emailExists -> {
                    if (emailExists) return Mono.error(new RuntimeException("Email ya registrado"));

                    User newUser = User.builder()
                            .username(request.getUsername())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .email(request.getEmail())
                            .role("USER")
                            .build();

                    return userRepository.save(newUser);
                })
                .map(saved -> AuthResponse.builder()
                        .token(jwtUtil.generateToken(saved.getUsername(), saved.getRole()))
                        .username(saved.getUsername())
                        .role(saved.getRole())
                        .expiresIn(jwtUtil.getExpiration())
                        .build());
    }
}