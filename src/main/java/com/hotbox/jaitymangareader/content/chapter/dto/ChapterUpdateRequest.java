package com.hotbox.jaitymangareader.content.chapter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChapterUpdateRequest(

        @NotBlank
        @Size(max = 50)
        String chapterNumber,

        @Size(max = 255)
        String title
) {
}
