package com.hotbox.jaitymangareader.auth.services;

import com.hotbox.jaitymangareader.auth.entity.RevokedToken;
import com.hotbox.jaitymangareader.auth.repository.RevokedTokenRepository;
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
