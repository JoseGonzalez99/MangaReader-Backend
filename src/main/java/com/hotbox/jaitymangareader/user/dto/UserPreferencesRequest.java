package com.hotbox.jaitymangareader.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserPreferencesRequest(
        @NotBlank String theme,
        @NotBlank String readingDirection,
        @NotBlank String defaultProvider
) {}
