package com.hotbox.jaitymangareader.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    AUTH_INVALID_CREDENTIALS("AUTH-001", "El correo o la contraseña son incorrectos", HttpStatus.UNAUTHORIZED),
    AUTH_ACCOUNT_DISABLED("AUTH-002", "Tu cuenta ha sido desactivada", HttpStatus.FORBIDDEN),
    AUTH_TOKEN_INVALID("AUTH-003", "Token inválido o expirado", HttpStatus.UNAUTHORIZED),
    AUTH_REFRESH_REVOKED("AUTH-004", "El token de sesión ya no es válido", HttpStatus.FORBIDDEN),

    USER_NOT_FOUND("USER-001", "El usuario solicitado no existe", HttpStatus.NOT_FOUND),
    USER_EMAIL_CONFLICT("USER-002", "Ya existe una cuenta con ese correo", HttpStatus.CONFLICT),
    USER_BLOCKED("USER-003", "Tu cuenta está bloqueada", HttpStatus.LOCKED),

    VALID_REQUIRED_FIELD("VALID-001", "Campo obligatorio faltante", HttpStatus.BAD_REQUEST),
    VALID_EMAIL_FORMAT("VALID-002", "Correo con formato inválido", HttpStatus.BAD_REQUEST),
    VALID_PASSWORD_WEAK("VALID-003", "Contraseña demasiado débil", HttpStatus.BAD_REQUEST),

    SYSTEM_ERROR("SYS-001", "Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM_UNAVAILABLE("SYS-002", "Servicio no disponible temporalmente", HttpStatus.SERVICE_UNAVAILABLE),

    SECURITY_IP_BLOCKED("SEC-002", "Has hecho demasiadas peticiones. Inténtalo más tarde", HttpStatus.TOO_MANY_REQUESTS);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
