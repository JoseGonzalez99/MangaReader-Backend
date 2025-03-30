package com.hotbox.jaitymangareader.interactioncontent.like.controller;

import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import com.hotbox.jaitymangareader.interactioncontent.like.entity.Like;
import com.hotbox.jaitymangareader.interactioncontent.like.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{mangaId}")
    public ResponseEntity<?> like(
            @PathVariable @NotBlank String mangaId,
            Authentication auth,
            HttpServletRequest request) {

        String userId = auth.getName();
        Like like = likeService.likeManga(userId, mangaId);
        return ResponseUtil.success(like, "Manga marcado como favorito", request);
    }

    @DeleteMapping("/{mangaId}")
    public ResponseEntity<?> unlike(
            @PathVariable @NotBlank String mangaId,
            Authentication auth,
            HttpServletRequest request) {

        likeService.unlikeManga(auth.getName(), mangaId);
        return ResponseUtil.noContent("Manga removido de favoritos", request);
    }

    @GetMapping
    public ResponseEntity<?> list(
            Authentication auth,
            HttpServletRequest request) {

        List<Like> likes = likeService.getUserLikes(auth.getName());
        return ResponseUtil.success(likes, "Favoritos obtenidos correctamente", request);
    }

    @GetMapping("/{mangaId}")
    public ResponseEntity<?> hasLiked(
            @PathVariable String mangaId,
            Authentication auth,
            HttpServletRequest request) {

        boolean liked = likeService.hasLiked(auth.getName(), mangaId);
        return ResponseUtil.success(liked, "Estado de favorito consultado", request);
    }
}
