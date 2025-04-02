package com.hotbox.jaitymangareader.content.manga.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MangaUpdateRequest(

        @NotBlank
        @Size(max = 255)
        String title,

        @Size(max = 255)
        String author,

        String description,

        String faviconUrl,

        String coverUrl
) {
}
