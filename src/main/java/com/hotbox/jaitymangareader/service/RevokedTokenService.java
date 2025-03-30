package com.hotbox.jaitymangareader.service;

import com.hotbox.jaitymangareader.entity.RevokedToken;
import com.hotbox.jaitymangareader.repository.RevokedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RevokedTokenService {

    private final RevokedTokenRepository revokedRepo;

    public void revokeToken(String jti, String userId) {
        RevokedToken token = RevokedToken.builder()
                .jti(jti)
                .userId(userId)
                .revokedAt(Instant.now())
                .build();

        revokedRepo.save(token);
    }

    public boolean isTokenRevoked(String jti) {
        return revokedRepo.existsById(jti);
    }
}
