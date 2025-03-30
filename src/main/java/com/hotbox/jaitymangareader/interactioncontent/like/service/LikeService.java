package com.hotbox.jaitymangareader.interactioncontent.like.service;

import com.hotbox.jaitymangareader.interactioncontent.like.entity.Like;
import com.hotbox.jaitymangareader.interactioncontent.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepo;

    public Like likeManga(String userId, String mangaId) {
        if (likeRepo.existsByUserIdAndMangaId(userId, mangaId)) {
            return likeRepo.findByUserIdAndMangaId(userId, mangaId).get();
        }

        Like like = Like.builder()
                .userId(userId)
                .mangaId(mangaId)
                .timestamp(Instant.now())
                .build();

        return likeRepo.save(like);
    }

    public void unlikeManga(String userId, String mangaId) {
        likeRepo.deleteByUserIdAndMangaId(userId, mangaId);
    }

    public List<Like> getUserLikes(String userId) {
        return likeRepo.findByUserId(userId);
    }

    public boolean hasLiked(String userId, String mangaId) {
        return likeRepo.existsByUserIdAndMangaId(userId, mangaId);
    }
}
