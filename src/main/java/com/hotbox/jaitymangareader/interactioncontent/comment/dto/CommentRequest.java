package com.hotbox.jaitymangareader.interactioncontent.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank String mangaId,
        String chapterId,
        @NotBlank String content
) {}
