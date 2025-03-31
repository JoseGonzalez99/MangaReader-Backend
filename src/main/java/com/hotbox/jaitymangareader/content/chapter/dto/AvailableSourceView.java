package com.hotbox.jaitymangareader.content.chapter.dto;

import java.util.UUID;

public record AvailableSourceView(
        UUID id,
        String languageCode,
        String providerName,
        String logoUrl,
        boolean isActive
) {}
