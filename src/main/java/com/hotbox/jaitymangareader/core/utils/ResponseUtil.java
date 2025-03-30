package com.hotbox.jaitymangareader.core.utils;

import com.hotbox.jaitymangareader.core.dto.ApiSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiSuccessResponse<T>> success(
            T data,
            String message,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiSuccessResponse.<T>builder()
                        .timestamp(Instant.now())
                        .status(200)
                        .message(message)
                        .path(request.getRequestURI())
                        .data(data)
                        .build()
        );
    }

    public static <T> ResponseEntity<ApiSuccessResponse<T>> created(
            T data,
            String message,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(201).body(
                ApiSuccessResponse.<T>builder()
                        .timestamp(Instant.now())
                        .status(201)
                        .message(message)
                        .path(request.getRequestURI())
                        .data(data)
                        .build()
        );
    }

    public static ResponseEntity<ApiSuccessResponse<Void>> noContent(
            String message,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(204).body(
                ApiSuccessResponse.<Void>builder()
                        .timestamp(Instant.now())
                        .status(204)
                        .message(message)
                        .path(request.getRequestURI())
                        .data(null)
                        .build()
        );
    }
}
