package com.hotbox.jaitymangareader.user.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document("user_context")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {

    @Id
    private String userId; // Igual al id de AppUser

    private Preferences preferences;

    private ReadingEntry lastRead;

    @Builder.Default
    private List<ReadingEntry> readingHistory = new ArrayList<>();

    @Builder.Default
    private List<String> favorites = new ArrayList<>();

    private Instant lastActiveAt;
}
