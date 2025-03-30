package com.hotbox.jaitymangareader.auth.repository;

import com.hotbox.jaitymangareader.auth.entity.RevokedToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RevokedTokenRepository extends MongoRepository<RevokedToken, String> {
}
