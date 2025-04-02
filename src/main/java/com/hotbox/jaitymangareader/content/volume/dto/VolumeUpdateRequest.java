package com.hotbox.jaitymangareader.content.volume.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VolumeUpdateRequest(

        @NotNull
        Integer volumeNumber,

        @NotBlank
        @Size(max = 255)
        String title,

        String coverUrl
) {
}
