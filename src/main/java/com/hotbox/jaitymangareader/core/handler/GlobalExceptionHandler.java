package com.hotbox.jaitymangareader.core.handler;

import com.hotbox.jaitymangareader.core.dto.ApiErrorResponse;
import com.hotbox.jaitymangareader.core.error.ApiException;
import com.hotbox.jaitymangareader.core.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        ErrorCode code = ex.getErrorCode();
        return build(code, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return build(ErrorCode.VALID_REQUIRED_FIELD, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("❌ Excepción no controlada", ex);
        return build(ErrorCode.SYSTEM_ERROR, request.getRequestURI());
    }

    private ResponseEntity<ApiErrorResponse> build(ErrorCode code, String path) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .status(code.getStatus().value())
                .error(code.getStatus().getReasonPhrase())
                .path(path)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
