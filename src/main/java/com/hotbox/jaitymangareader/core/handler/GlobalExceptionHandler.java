package com.hotbox.jaitymangareader.core.handler;

import com.hotbox.jaitymangareader.core.error.ApiException;
import com.hotbox.jaitymangareader.core.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex, HttpServletRequest request) {
        var error = ex.getError(); // es BaseErrorCode
        return ResponseEntity.status(error.getStatus()).body(buildErrorBody(
                error.getStatus(),
                error.getCode(),
                error.getMessage(),
                request.getRequestURI()
        ));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String msg = fieldError != null
                ? String.format("Campo inválido: %s (%s)", fieldError.getField(), fieldError.getDefaultMessage())
                : "Error de validación";

        return ResponseEntity
                .badRequest()
                .body(buildErrorBody(HttpStatus.BAD_REQUEST, ErrorCode.VALID_REQUIRED_FIELD.getCode(), msg, request.getRequestURI()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(buildErrorBody(HttpStatus.FORBIDDEN, ErrorCode.AUTH_ACCOUNT_DISABLED.getCode(), "Acceso denegado", request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex, HttpServletRequest request) {
        ex.printStackTrace(); // opcional para debug
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.SYSTEM_ERROR.getCode(), "Error inesperado", request.getRequestURI()));
    }

    private Map<String, Object> buildErrorBody(HttpStatus status, String code, String message, String path) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", Instant.now());
        error.put("status", status.value());
        error.put("code", code);
        error.put("message", message);
        error.put("path", path);
        return error;
    }
}
