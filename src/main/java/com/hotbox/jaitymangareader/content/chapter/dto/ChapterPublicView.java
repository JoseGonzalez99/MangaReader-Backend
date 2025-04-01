package com.hotbox.jaitymangareader.content.chapter.dto;

import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;

import java.time.Instant;
import java.util.UUID;

public record ChapterPublicView(
        UUID id,
        UUID volumeId,
        String chapterNumber,
        String title,
        Instant createdAt,
        Instant updatedAt
) {
    public static ChapterPublicView from(Chapter chapter) {
        return new ChapterPublicView(
                chapter.getId(),
                chapter.getVolume().getId(),
                chapter.getChapterNumber(),
                chapter.getTitle(),
                chapter.getCreatedAt(),
                chapter.getUpdatedAt()
        );
    }
}
