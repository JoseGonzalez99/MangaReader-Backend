package com.hotbox.jaitymangareader.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiSuccessResponse<T>(
        Instant timestamp,
        int status,
        String message,
        String path,
        T data
) {}
