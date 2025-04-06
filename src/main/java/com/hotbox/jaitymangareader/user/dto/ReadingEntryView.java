package com.hotbox.jaitymangareader.user.dto;

import com.hotbox.jaitymangareader.user.entity.ReadingEntry;
import java.time.Instant;
import java.util.List;

public record ReadingEntryView(
        String mangaId,
        String chapterId,
        int lastPageRead,
        Instant lastReadAt,
        ReadingStatus status,
        List<String> tags
) {
    public static ReadingEntryView from(ReadingEntry e) {
        return new ReadingEntryView(
                e.getMangaId(),
                e.getChapterId(),
                e.getLastPageRead(),
                e.getLastReadAt(),
                e.getStatus(),
                e.getTags()
        );
    }
}
