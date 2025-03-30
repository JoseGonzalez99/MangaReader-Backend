package com.hotbox.jaitymangareader.auth.repository;

import com.hotbox.jaitymangareader.auth.entity.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(String userId);
    List<RefreshToken> findAllByUserIdOrderByExpiryDateDesc(String userId);
    Optional<RefreshToken> findByIdAndUserId(String tokenId, String userId);

}
