package com.miempresa.reactiveapi.controller;

import com.miempresa.reactiveapi.dto.*;
import com.miempresa.reactiveapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.status(HttpStatus.CONFLICT).<AuthResponse>build()
                ));
    }
}