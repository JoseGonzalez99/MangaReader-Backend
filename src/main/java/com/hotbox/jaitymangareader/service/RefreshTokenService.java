package com.hotbox.jaitymangareader.service;

import com.hotbox.jaitymangareader.dto.RefreshTokenView;
import com.hotbox.jaitymangareader.entity.AppUser;
import com.hotbox.jaitymangareader.entity.RefreshToken;
import com.hotbox.jaitymangareader.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshRepo;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshExpirationMs;

    public RefreshToken createRefreshToken(AppUser user, String ip, String userAgent) {
        RefreshToken token = RefreshToken.builder()
                .userId(user.getId())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
                .revoked(false)
                .ipAddress(ip)
                .userAgent(userAgent)
                .build();

        return refreshRepo.save(token);
    }


    public Optional<RefreshToken> verifyToken(String tokenStr) {
        return refreshRepo.findByToken(tokenStr)
                .filter(t -> !t.isRevoked())
                .filter(t -> t.getExpiryDate().isAfter(Instant.now()));
    }

    public void revokeTokensForUser(String userId) {
        refreshRepo.deleteByUserId(userId);
    }



    public List<RefreshTokenView> findUserSessions(String userId) {
        return refreshRepo.findAllByUserIdOrderByExpiryDateDesc(userId)
                .stream()
                .map(t -> new RefreshTokenView(
                        t.getId(),
                        t.getUserId(),
                        t.getToken(),
                        t.getExpiryDate(),
                        t.isRevoked(),
                        t.getIpAddress(),
                        t.getUserAgent()
                ))
                .toList();
    }

    public boolean revokeTokenById(String userId, String tokenId) {
        Optional<RefreshToken> opt = refreshRepo.findByIdAndUserId(tokenId, userId);

        if (opt.isEmpty()) return false;

        RefreshToken token = opt.get();
        token.setRevoked(true);
        refreshRepo.save(token);
        return true;
    }

}
