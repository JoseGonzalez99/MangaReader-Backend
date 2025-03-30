package com.hotbox.jaitymangareader.config.mongo;

import com.hotbox.jaitymangareader.audit.entity.UserEventLog;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Sort.Direction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoIndexInitializer {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void createIndexes() {
        mongoTemplate.indexOps(UserEventLog.class).ensureIndex(
                new Index().on("userId", Direction.ASC).on("timestamp", Direction.DESC)
        );

        mongoTemplate.indexOps(UserEventLog.class).ensureIndex(
                new Index().on("userId", Direction.ASC).on("eventType", Direction.ASC).on("timestamp", Direction.DESC)
        );

        mongoTemplate.indexOps(UserEventLog.class).ensureIndex(
                new Index().on("eventType", Direction.ASC).on("timestamp", Direction.DESC)
        );

        System.out.println("✅ Índices creados en user_events.");
    }
}
