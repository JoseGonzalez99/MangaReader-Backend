package com.hotbox.jaitymangareader.interactioncontent.like.repository;

import com.hotbox.jaitymangareader.interactioncontent.like.entity.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends MongoRepository<Like, String> {
    List<Like> findByUserId(String userId);
    Optional<Like> findByUserIdAndMangaId(String userId, String mangaId);
    void deleteByUserIdAndMangaId(String userId, String mangaId);
    boolean existsByUserIdAndMangaId(String userId, String mangaId);
}
