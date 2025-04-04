package com.hotbox.jaitymangareader.user.repository;

import com.hotbox.jaitymangareader.user.entity.UserContext;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserContextRepository extends MongoRepository<UserContext, String> {
}
