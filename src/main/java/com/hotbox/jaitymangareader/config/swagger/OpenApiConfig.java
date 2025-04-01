package com.hotbox.jaitymangareader.config.swagger;

import org.springdoc.core.models.GroupedOpenApi;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    // 🔐 Auth y perfil del usuario
    @Bean
    public GroupedOpenApi authGroup() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/api/v1/auth/**", "/api/v1/me/**")
                .build();
    }

    // 👩‍💻 Admin
    @Bean
    public GroupedOpenApi adminGroup() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/api/v1/admin/**")
                .build();
    }

    // 📚 Manga y su contenido
    @Bean
    public GroupedOpenApi contentGroup() {
        return GroupedOpenApi.builder()
                .group("content")
                .pathsToMatch(
                        "/api/v1/mangas/**",
                        "/api/v1/volumes/**",
                        "/api/v1/chapters/**",
                        "/api/v1/pages/**",
                        "/api/v1/providers/**",
                        "/api/v1/sources/**")
                .build();
    }

    // 🌐 Endpoints públicos sin auth
    @Bean
    public GroupedOpenApi publicGroup() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch(
                        "/api/v1/public/pages/**",
                        "/api/v1/public/sources/**")
                .build();
    }
}
