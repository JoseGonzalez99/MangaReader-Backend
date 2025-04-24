package com.hotbox.jaitymangareader.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://web.jaity.com",
                        "capacitor://localhost",   // iOS
                        "http://localhost:8080",   // dev web
                        "http://localhost:8081",   // expo web
                        "file://",                 // Android
                        "http://10.0.2.2:8080"     // emulador Android
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // Encabezados permitidos
                .allowCredentials(true);

    }
}
