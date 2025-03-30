package com.hotbox.jaitymangareader.interactioncontent.comment.controller;

import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import com.hotbox.jaitymangareader.interactioncontent.comment.dto.CommentRequest;
import com.hotbox.jaitymangareader.interactioncontent.comment.entity.Comment;
import com.hotbox.jaitymangareader.interactioncontent.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/v1/me/comments")
    public ResponseEntity<?> create(
            @RequestBody @Valid CommentRequest request,
            Authentication auth,
            HttpServletRequest httpRequest) {

        Comment comment = commentService.create(auth.getName(), request);
        return ResponseUtil.created(comment, "Comentario publicado", httpRequest);
    }

    @DeleteMapping("/api/v1/me/comments/{id}")
    public ResponseEntity<?> delete(
            @PathVariable @NotBlank String id,
            Authentication auth,
            HttpServletRequest httpRequest) {

        commentService.delete(id, auth.getName());
        return ResponseUtil.noContent("Comentario eliminado", httpRequest);
    }

    @GetMapping("/api/v1/comments")
    public ResponseEntity<?> getAll(
            @RequestParam @NotBlank String mangaId,
            @RequestParam(required = false) String chapterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            HttpServletRequest httpRequest) {

        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = commentService.getByManga(mangaId, chapterId, pageable);
        return ResponseUtil.success(comments, "Comentarios obtenidos correctamente", httpRequest);
    }
}
