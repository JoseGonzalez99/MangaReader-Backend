package com.hotbox.jaitymangareader.interactioncontent.comment.dto;


import com.hotbox.jaitymangareader.interactioncontent.comment.entity.Comment;

import java.time.Instant;

public record CommentView(
                          String id,

                          String userId,

                          String mangaId,

                          // ⚠️ Referencia opcional al capítulo lógico
                          String chapterId, // Puede ser null si es un comentario general

                          String content,
                          Instant createdAt
)
{

    public static CommentView from(Comment comment) {
        return new CommentView(
                comment.getId(),
                comment.getUserId(),
                comment.getMangaId(),
                comment.getChapterId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}


