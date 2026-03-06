package com.miempresa.reactiveapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    private Long id;

    private String username;
    private String password;
    private String email;

    @Builder.Default
    private String role = "USER";

    @Builder.Default
    private Boolean enabled = true;

    private OffsetDateTime createdAt;
}