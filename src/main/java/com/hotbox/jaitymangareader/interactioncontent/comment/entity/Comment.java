package com.hotbox.jaitymangareader.interactioncontent.comment.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    private String id;

    private String userId;
    private String mangaId;
    private String chapterId; // Puede ser null
    private String content;

    private Instant createdAt;
}
