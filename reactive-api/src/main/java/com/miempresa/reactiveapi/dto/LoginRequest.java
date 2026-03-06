package com.miempresa.reactiveapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username requerido")
    private String username;

    @NotBlank(message = "Password requerido")
    private String password;
}