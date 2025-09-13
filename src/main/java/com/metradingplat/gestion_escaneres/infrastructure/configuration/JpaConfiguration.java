package com.metradingplat.gestion_escaneres.infrastructure.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("sistema");
    }
}