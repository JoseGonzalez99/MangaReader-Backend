package com.hotbox.jaitymangareader.content.page.dto;

import com.hotbox.jaitymangareader.content.page.entity.Page;

import java.time.Instant;
import java.util.UUID;

public record PagePublicView(
        UUID id,
        int pageNumber,
        String imageUrl,
        Instant createdAt,
        Instant updatedAt
) {
    public static PagePublicView from(Page page) {
        return new PagePublicView(
                page.getId(),
                page.getPageNumber(),
                page.getImageUrl(),
                page.getCreatedAt(),
                page.getUpdatedAt()
        );
    }
}
