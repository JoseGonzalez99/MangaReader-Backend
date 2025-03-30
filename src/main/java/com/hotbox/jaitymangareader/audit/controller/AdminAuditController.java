package com.hotbox.jaitymangareader.audit.controller;

import com.hotbox.jaitymangareader.audit.entity.UserEventLog;
import com.hotbox.jaitymangareader.audit.repository.UserEventLogRepository;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/audit")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class AdminAuditController {

    private final UserEventLogRepository eventRepo;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAuditLogs(
            @PathVariable @NotBlank String userId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant since,
            HttpServletRequest request
    ) {
        List<UserEventLog> result;

        if (type != null && since != null) {
            result = eventRepo.findByUserIdAndEventTypeAndTimestampAfterOrderByTimestampDesc(userId, type, since);
        } else if (type != null) {
            result = eventRepo.findByUserIdAndEventTypeOrderByTimestampDesc(userId, type);
        } else if (since != null) {
            result = eventRepo.findByUserIdAndTimestampAfterOrderByTimestampDesc(userId, since);
        } else {
            result = eventRepo.findByUserIdOrderByTimestampDesc(userId);
        }

        return ResponseUtil.success(result, "Eventos de auditoría recuperados correctamente", request);
    }
}

