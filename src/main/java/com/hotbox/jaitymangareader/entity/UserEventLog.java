package com.hotbox.jaitymangareader.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_events")
public class UserEventLog {

    @Id
    private String id;

    private String userId;
    private String eventType;      // ej: "LIKE", "COMMENT", "LOGIN", "NAVIGATION", etc.
    private Instant timestamp;

    private String resourceId;     // ID del recurso relacionado (opcional)
    private String resourceType;   // ej: "MANGA", "COMMENT", etc.

    private Map<String, Object> data; // Payload flexible y extensible
}
