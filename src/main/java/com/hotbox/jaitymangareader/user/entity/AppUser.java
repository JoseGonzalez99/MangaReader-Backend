package com.hotbox.jaitymangareader.user.entity;

import com.hotbox.jaitymangareader.config.security.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class AppUser {
    @Id
    private String id;

    private String email;
    private String password;
    private Role role;
    private boolean enabled;
    private Instant createdAt;

}
