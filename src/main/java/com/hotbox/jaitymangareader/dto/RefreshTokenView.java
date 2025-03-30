package com.hotbox.jaitymangareader.dto;

import java.time.Instant;

public record RefreshTokenView(
        String id,
        String userId,
        String token,
        Instant expiryDate,
        boolean revoked,
        String ipAddress,
        String userAgent
) {}
