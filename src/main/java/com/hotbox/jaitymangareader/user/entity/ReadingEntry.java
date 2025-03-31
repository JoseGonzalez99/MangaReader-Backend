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

    // ⚠️ Puede ser null si solo se guarda la fuente
    private String chapterId;

    // ✅ Nuevo: referencia a fuente específica
    private String chapterSourceId;

    private int lastPageRead;
    private Instant lastReadAt;

    private ReadingStatus status; // ej: IN_PROGRESS, COMPLETED
    private List<String> tags;    // ej: ["dark", "comedy", "isekai"]
}
