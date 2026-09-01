package br.com.fiap.orbitguard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI orbitGuardOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("OrbitGuard API")
                        .description("API REST da solucao Global Solution 2026/1 para alertas climaticos com dados orbitais.")
                        .version("1.0.0")
                        .contact(new Contact().name("FIAP 2TDS")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .schemaRequirement("bearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
    }
}
