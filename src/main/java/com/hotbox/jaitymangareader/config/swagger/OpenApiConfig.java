package com.hotbox.jaitymangareader.config.swagger;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jaity Manga Reader API")
                        .version("v1")
                        .description("Documentación interactiva de la API de Jaity"))
                .components(new Components()
                        .addSecuritySchemes("BearerJWT", jwtSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("BearerJWT"));
    }

    private SecurityScheme jwtSecurityScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .description("Token JWT (con Bearer al inicio)")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("jaity-api")
                .pathsToMatch("/api/v1/auth/**", "/api/v1/me/**", "/api/v1/admin/**")
                .build();
    }
}
