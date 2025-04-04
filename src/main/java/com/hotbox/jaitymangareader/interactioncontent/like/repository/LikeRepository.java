package com.hotbox.jaitymangareader.interactioncontent.like.repository;

import com.hotbox.jaitymangareader.interactioncontent.like.entity.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LikeRepository extends MongoRepository<Like, String> {
    boolean existsByUserIdAndMangaId(String userId, String mangaId);
    void deleteByUserIdAndMangaId(String userId, String mangaId);
    List<Like> findByUserId(String userId);
}
