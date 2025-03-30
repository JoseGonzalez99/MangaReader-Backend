package com.hotbox.jaitymangareader.repository;

import com.hotbox.jaitymangareader.entity.RevokedToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RevokedTokenRepository extends MongoRepository<RevokedToken, String> {
}
