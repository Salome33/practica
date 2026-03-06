package com.miempresa.reactiveapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Ruta accesible por cualquier usuario autenticado
    @GetMapping
    public Flux<Map<String, Object>> getAllProducts(Authentication auth) {
        return Flux.just(
                Map.of("id", 1, "name", "Laptop", "price", 1200.00, "user", auth.getName()),
                Map.of("id", 2, "name", "Mouse",  "price", 25.00,   "user", auth.getName())
        );
    }

    // Solo ADMIN puede crear productos
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Map<String, Object>> createProduct(@RequestBody Map<String, Object> body) {
        return Mono.just(Map.of("message", "Producto creado", "data", body));
    }

    // Ruta solo para ADMIN
    @GetMapping("/admin/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Map<String, Object>> adminStats() {
        return Mono.just(Map.of("totalProducts", 2, "totalUsers", 100));
    }
}