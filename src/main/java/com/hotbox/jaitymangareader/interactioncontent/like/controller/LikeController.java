package com.hotbox.jaitymangareader.interactioncontent.like.controller;

import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import com.hotbox.jaitymangareader.interactioncontent.like.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me/likes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENT') or hasRole('STAFF')")
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<?> getLikes(Authentication auth, HttpServletRequest req) {
        String userId = auth.getName();
        return ResponseUtil.success(
                likeService.getUserLikes(userId),
                "Likes obtenidos correctamente.",
                req
        );
    }

    @GetMapping("/{mangaId}")
    public ResponseEntity<?> hasLiked(
            @PathVariable @NotBlank String mangaId,
            Authentication auth,
            HttpServletRequest req) {

        boolean liked = likeService.hasLiked(auth.getName(), mangaId);
        return ResponseUtil.success(liked, liked ? "Ya diste like." : "Aún no le diste like.", req);
    }

    @PostMapping("/{mangaId}")
    public ResponseEntity<?> like(
            @PathVariable @NotBlank String mangaId,
            Authentication auth,
            HttpServletRequest req) {

        likeService.like(auth.getName(), mangaId);
        return ResponseUtil.created(null, "Like agregado correctamente.", req);
    }

    @DeleteMapping("/{mangaId}")
    public ResponseEntity<?> unlike(
            @PathVariable @NotBlank String mangaId,
            Authentication auth,
            HttpServletRequest req) {

        likeService.unlike(auth.getName(), mangaId);
        return ResponseUtil.noContent("Like removido correctamente.", req);
    }
}
