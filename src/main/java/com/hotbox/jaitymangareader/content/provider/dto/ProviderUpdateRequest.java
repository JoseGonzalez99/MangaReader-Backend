package com.hotbox.jaitymangareader.content.provider.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProviderUpdateRequest(

        @NotBlank
        @Size(max = 100)
        String providerName,

        @NotBlank
        @Size(max = 10)
        String providedLang,

        @Size(max = 255)
        String logoUrl,

        boolean isActive
) {
}
