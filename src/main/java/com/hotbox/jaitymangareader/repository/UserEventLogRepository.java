package com.hotbox.jaitymangareader.repository;

import com.hotbox.jaitymangareader.entity.UserEventLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.time.Instant;

public interface UserEventLogRepository extends MongoRepository<UserEventLog, String> {
    List<UserEventLog> findByUserIdOrderByTimestampDesc(String userId);
    List<UserEventLog> findByUserIdAndEventTypeOrderByTimestampDesc(String userId, String eventType);
    List<UserEventLog> findByUserIdAndTimestampAfterOrderByTimestampDesc(String userId, Instant since);
    List<UserEventLog> findByUserIdAndEventTypeAndTimestampAfterOrderByTimestampDesc(String userId, String eventType, Instant since);


}
