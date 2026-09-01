package br.com.fiap.orbitguard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "orbitguard-api",
                "timestamp", OffsetDateTime.now());
    }
}
