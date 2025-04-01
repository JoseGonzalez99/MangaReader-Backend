package com.hotbox.jaitymangareader.content.manga.dto;

import com.hotbox.jaitymangareader.content.manga.entity.Manga;

import java.time.Instant;
import java.util.UUID;

public record MangaPublicView(
        UUID id,
        String title,
        String author,
        String description,
        String coverUrl,
        String faviconUrl,
        Double rating,
        int volumesCount,
        int chaptersCount,
        Instant createdAt,
        Instant updatedAt
) {
    public static MangaPublicView from(Manga manga) {
        return new MangaPublicView(
                manga.getId(),
                manga.getTitle(),
                manga.getAuthor(),
                manga.getDescription(),
                manga.getCoverUrl(),
                manga.getFaviconUrl(),
                manga.getRating(),
                manga.getVolumesCount(),
                manga.getChaptersCount(),
                manga.getCreatedAt(),
                manga.getUpdatedAt()
        );
    }
}
