package com.hotbox.jaitymangareader.core.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DomainErrorCode implements BaseErrorCode {

    // 📚 Manga
    MANGA_NOT_FOUND("MANGA-001", "El manga solicitado no existe", HttpStatus.NOT_FOUND),
    MANGA_DUPLICATE("MANGA-002", "Ya existe un manga con ese título", HttpStatus.CONFLICT),

    // 📘 Volumen
    VOLUME_NOT_FOUND("VOLUME-001", "Volumen no encontrado", HttpStatus.NOT_FOUND),

    // 📖 Capítulo
    CHAPTER_NOT_FOUND("CHAPTER-001", "Capítulo no encontrado", HttpStatus.NOT_FOUND),
    CHAPTER_DUPLICATE("CHAPTER-002", "Ya existe un capítulo con ese número", HttpStatus.CONFLICT),

    // 📄 Página
    PAGE_NOT_FOUND("PAGE-001", "Página no encontrada", HttpStatus.NOT_FOUND),

    // 🌐 Proveedor
    PROVIDER_NOT_FOUND("PROVIDER-001", "Proveedor no encontrado", HttpStatus.NOT_FOUND),
    PROVIDER_INACTIVE("PROVIDER-002", "El proveedor está deshabilitado", HttpStatus.BAD_REQUEST),

    // 🔄 Fuentes de capítulo
    CHAPTER_SOURCE_NOT_FOUND("CHAPTERSRC-001", "Fuente del capítulo no encontrada", HttpStatus.NOT_FOUND),
    CHAPTER_SOURCE_INACTIVE("CHAPTERSRC-002", "La fuente del capítulo está inactiva", HttpStatus.BAD_REQUEST),

    // 👥 Usuario
    USER_CONTEXT_NOT_FOUND("USERCTX-001", "No se encontró información de usuario", HttpStatus.NOT_FOUND),
    INVALID_READING_INPUT("USERCTX-002", "Parámetros inválidos para registrar progreso de lectura", HttpStatus.BAD_REQUEST),
    USER_NO_LAST_READ("USERCTX-003", "El usuario aún no tiene ninguna lectura registrada", HttpStatus.NOT_FOUND),
    USER_NO_READING_HISTORY("USERCTX-004", "No hay historial de lectura disponible", HttpStatus.NOT_FOUND),


    // 💬 Comentarios
    COMMENT_NOT_FOUND("COMMENT-001", "Comentario no encontrado", HttpStatus.NOT_FOUND),
    COMMENT_FORBIDDEN("COMMENT-002", "No tienes permisos para modificar este comentario", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;

    DomainErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
