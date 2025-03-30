package com.hotbox.jaitymangareader.core.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiErrorResponse(
        String code,
        String message,
        int status,
        String error,
        String path,
        Instant timestamp
) {}
