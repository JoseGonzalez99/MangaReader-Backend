package com.hotbox.jaitymangareader.config;

import com.hotbox.jaitymangareader.entity.AppUser;
import com.hotbox.jaitymangareader.entity.Role;
import com.hotbox.jaitymangareader.entity.UserEventLog;
import com.hotbox.jaitymangareader.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;


@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;
    private final AuditProperties auditProperties;

    @Override
    public void run(ApplicationArguments args) {
        // Crear colecciones necesarias
        createCollectionIfMissing("users");
        createCollectionIfMissing("refresh_tokens");
        createCollectionIfMissing("user_events");

        // Crear índices para user_events
        mongoTemplate.indexOps(UserEventLog.class).ensureIndex(
                new Index().on("userId", Direction.ASC).on("timestamp", Direction.DESC)
        );

        mongoTemplate.indexOps(UserEventLog.class).ensureIndex(
                new Index().on("userId", Direction.ASC).on("eventType", Direction.ASC).on("timestamp", Direction.DESC)
        );

        mongoTemplate.indexOps(UserEventLog.class).ensureIndex(
                new Index().on("eventType", Direction.ASC).on("timestamp", Direction.DESC)
        );

        // TTL (si está definido y es mayor a 0)
        if (auditProperties.getTtlDays() > 0) {
            mongoTemplate.indexOps(UserEventLog.class).ensureIndex(
                    new Index().on("timestamp", Direction.ASC)
                            .expire((long) auditProperties.getTtlDays() * 24 * 60 * 60)
            );
            System.out.println("⏳ TTL activado para 'user_events': " + auditProperties.getTtlDays() + " días");
        } else {
            System.out.println("♾️ TTL desactivado para 'user_events'. Los eventos no expirarán.");
        }

        // Crear usuario admin si no existe
        String adminEmail = "admin@jaity.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            AppUser admin = AppUser.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.STAFF)
                    .enabled(true)
                    .createdAt(Instant.now())
                    .build();

            userRepository.save(admin);
            System.out.println("✅ Usuario admin creado: " + adminEmail + " / admin123");
        } else {
            System.out.println("ℹ️ Usuario admin ya existe, no se creó nuevamente.");
        }
    }

    private void createCollectionIfMissing(String name) {
        if (!mongoTemplate.collectionExists(name)) {
            mongoTemplate.createCollection(name);
            System.out.printf("📁 Colección '%s' creada.%n", name);
        }
    }
}
