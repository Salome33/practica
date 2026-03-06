package com.miempresa.reactiveapi.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories(basePackages = "com.miempresa.reactiveapi.repository")
public class R2dbcConfig {

    /**
     * Ejecuta schema.sql automáticamente al arrancar la aplicación.
     * Crea las tablas si no existen (CREATE TABLE IF NOT EXISTS).
     */
    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql")
        );
        initializer.setDatabasePopulator(populator);

        return initializer;
    }
}