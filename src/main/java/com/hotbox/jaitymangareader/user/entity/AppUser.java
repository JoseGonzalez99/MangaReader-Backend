package com.hotbox.jaitymangareader.user.entity;

import com.hotbox.jaitymangareader.config.security.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    private String id;

    private String email;
    private String password; // puede ser null si el usuario viene solo por OAuth
    private Role role;
    private boolean enabled;
    private Instant createdAt;

    // Nuevos campos para OAuth
    private String fullName;
    private String photoUrl;
    private String provider;    // "google", "github", etc.
    private String providerId;  // ID del proveedor
}
