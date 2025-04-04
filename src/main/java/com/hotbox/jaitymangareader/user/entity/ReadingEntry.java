package com.hotbox.jaitymangareader.user.entity;

import com.hotbox.jaitymangareader.user.dto.ReadingStatus;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadingEntry {

    private String mangaId;
    private String chapterId;
    private int lastPageRead;
    private Instant lastReadAt;

    private ReadingStatus status; // IN_PROGRESS, FINISHED, ABANDONED, etc.
    private List<String> tags;
}
