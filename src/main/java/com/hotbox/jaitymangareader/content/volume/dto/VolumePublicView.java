package com.hotbox.jaitymangareader.content.volume.dto;

import com.hotbox.jaitymangareader.content.volume.entity.Volume;

import java.time.Instant;
import java.util.UUID;

public record VolumePublicView(
        UUID id,
        UUID mangaId,
        int volumeNumber,
        String title,
        String coverUrl,
        Instant createdAt,
        Instant updatedAt
) {
    public static VolumePublicView from(Volume volume) {
        return new VolumePublicView(
                volume.getId(),
                volume.getManga().getId(),
                volume.getVolumeNumber(),
                volume.getTitle(),
                volume.getCoverUrl(),
                volume.getCreatedAt(),
                volume.getUpdatedAt()
        );
    }
}
