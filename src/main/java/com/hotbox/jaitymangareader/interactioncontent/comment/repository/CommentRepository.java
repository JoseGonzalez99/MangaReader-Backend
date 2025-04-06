package com.hotbox.jaitymangareader.interactioncontent.comment.repository;

import com.hotbox.jaitymangareader.interactioncontent.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByMangaId(String mangaId, Pageable pageable);
    List<Comment> findByMangaIdAndChapterId(String mangaId, String chapterId, Pageable pageable);
    List<Comment> findByUserId(String userId);
    List<Comment> findByMangaIdOrderByCreatedAtDesc(String mangaId);
    
}
