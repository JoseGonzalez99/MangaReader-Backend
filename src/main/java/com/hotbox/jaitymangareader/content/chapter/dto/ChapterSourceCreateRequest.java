package com.hotbox.jaitymangareader.content.chapter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ChapterSourceCreateRequest(

        @NotNull
        UUID chapterId,

        @NotNull
        UUID providerId,

        @NotBlank
        @Size(max = 10)
        String languageCode
) {
}
