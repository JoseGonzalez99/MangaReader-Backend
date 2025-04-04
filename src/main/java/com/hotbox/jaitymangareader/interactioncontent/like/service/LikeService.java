package com.hotbox.jaitymangareader.interactioncontent.like.service;

import com.hotbox.jaitymangareader.interactioncontent.like.entity.Like;
import com.hotbox.jaitymangareader.interactioncontent.like.repository.LikeRepository;
import com.hotbox.jaitymangareader.user.service.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserContextService userContextService;

    public void like(String userId, String mangaId) {
        if (!likeRepository.existsByUserIdAndMangaId(userId, mangaId)) {
            Like like = Like.builder()
                    .userId(userId)
                    .mangaId(mangaId)
                    .timestamp(Instant.now())
                    .build();

            likeRepository.save(like);
        }

        // Actualizar favoritos en el contexto
        var ctx = userContextService.getByUserId(userId);
        if (!ctx.getFavorites().contains(mangaId)) {
            ctx.getFavorites().add(mangaId);
            userContextService.update(userId, ctx);
        }
    }

    public void unlike(String userId, String mangaId) {
        likeRepository.deleteByUserIdAndMangaId(userId, mangaId);

        var ctx = userContextService.getByUserId(userId);
        ctx.getFavorites().remove(mangaId);
        userContextService.update(userId, ctx);
    }

    public boolean hasLiked(String userId, String mangaId) {
        return likeRepository.existsByUserIdAndMangaId(userId, mangaId);
    }

    public List<Like> getUserLikes(String userId) {
        return likeRepository.findByUserId(userId);
    }
}
