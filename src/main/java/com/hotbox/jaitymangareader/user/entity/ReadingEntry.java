package com.hotbox.jaitymangareader.user.entity;

import com.hotbox.jaitymangareader.user.dto.ReadingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private ReadingStatus status; // opcional, pero útil
    private List<String> tags;    // opcional para analítica
}
