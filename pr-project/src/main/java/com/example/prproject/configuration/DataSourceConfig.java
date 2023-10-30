package com.example.prproject.configuration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@EntityScan("com.example.prproject.domain")
@EnableJpaRepositories("com.example.prproject.dao")
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@AllArgsConstructor
public class DataSourceConfig {

    private Clock clock;

    @Bean
    DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now(clock));
    }
}
