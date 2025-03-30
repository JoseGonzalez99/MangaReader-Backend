package com.hotbox.jaitymangareader.service;

import com.hotbox.jaitymangareader.entity.UserEventLog;
import com.hotbox.jaitymangareader.repository.UserEventLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuditEventService {

    private final UserEventLogRepository eventRepo;

    public void logEvent(String userId, String eventType, Map<String, Object> data) {
        eventRepo.save(UserEventLog.builder()
                .userId(userId)
                .eventType(eventType)
                .timestamp(Instant.now())
                .data(data)
                .build());
    }

    public void logEvent(String userId, String eventType, String resourceType, String resourceId, Map<String, Object> data) {
        eventRepo.save(UserEventLog.builder()
                .userId(userId)
                .eventType(eventType)
                .resourceType(resourceType)
                .resourceId(resourceId)
                .timestamp(Instant.now())
                .data(data)
                .build());
    }

    // 🔁 Métodos semánticos para acciones comunes
    public void logLogin(String userId, String ip, String userAgent) {
        logEvent(userId, "LOGIN", Map.of("ip", ip, "userAgent", userAgent));
    }

    public void logAccess(String userId, String jti, String ip, String userAgent, String path) {
        logEvent(userId, "ACCESS", Map.of(
                "jti", jti,
                "ip", ip,
                "userAgent", userAgent,
                "path", path
        ));
    }

    public void logLogout(String userId, String jti, String ip) {
        logEvent(userId, "LOGOUT", Map.of(
                "jti", jti,
                "ip", ip
        ));
    }

    public void logLike(String userId, String mangaId) {
        logEvent(userId, "LIKE", "MANGA", mangaId, Map.of("liked", true));
    }

    public void logComment(String userId, String chapterId, String text) {
        logEvent(userId, "COMMENT", "CHAPTER", chapterId, Map.of(
                "text", text,
                "length", text.length()
        ));
    }

    public void logFailedLogin(String email, String ip, String userAgent) {
        logEvent(email, "FAILED_LOGIN", Map.of("ip", ip, "userAgent", userAgent));
    }

    public void logPasswordChange(String userId, String ip) {
        logEvent(userId, "PASSWORD_CHANGE", Map.of("ip", ip));
    }

    public void logEmailChange(String userId, String oldEmail, String newEmail, String ip) {
        logEvent(userId, "EMAIL_CHANGE", Map.of(
                "oldEmail", oldEmail,
                "newEmail", newEmail,
                "ip", ip
        ));
    }


}
