package com.hotbox.jaitymangareader.content.chapter.dto;

import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;

import java.util.UUID;

public record AvailableSourceView(
        UUID id,
        String languageCode,
        String providerName,
        String logoUrl,
        boolean isActive
) {
    public static AvailableSourceView from(ChapterSource source) {
        return new AvailableSourceView(
                source.getId(),
                source.getLanguageCode(),
                source.getProvider().getProviderName(),
                source.getProvider().getLogoUrl(),
                source.isActive()
        );
    }
}
