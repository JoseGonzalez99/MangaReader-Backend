package com.hotbox.jaitymangareader.interactioncontent.like.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("likes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    private String id;

    private String userId;
    private String mangaId;
    private Instant timestamp;
}
