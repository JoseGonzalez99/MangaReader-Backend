package com.hotbox.jaitymangareader.user.dto;

import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import com.hotbox.jaitymangareader.user.entity.ReadingEntry;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ReadingEntryView(
        UUID mangaId,
        String mangaTitle,
        String coverUrl,
        String faviconUrl,
        String chapterId,
        int lastPageRead,
        Instant lastReadAt,
        String status
) {
    public static ReadingEntryView from(ReadingEntry entry, Manga manga) {
        return ReadingEntryView.builder()
                .mangaId(manga.getId())
                .mangaTitle(manga.getTitle())
                .coverUrl(manga.getCoverUrl())
                .faviconUrl(manga.getFaviconUrl())
                .chapterId(entry.getChapterId())
                .lastPageRead(entry.getLastPageRead())
                .lastReadAt(entry.getLastReadAt())
                .status(entry.getStatus().name())
                .build();
    }
}

