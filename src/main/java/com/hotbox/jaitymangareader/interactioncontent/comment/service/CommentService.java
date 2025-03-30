package com.hotbox.jaitymangareader.interactioncontent.comment.service;

import com.hotbox.jaitymangareader.interactioncontent.comment.dto.CommentRequest;
import com.hotbox.jaitymangareader.interactioncontent.comment.entity.Comment;
import com.hotbox.jaitymangareader.interactioncontent.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepo;

    public Comment create(String userId, CommentRequest request) {
        Comment comment = Comment.builder()
                .userId(userId)
                .mangaId(request.mangaId())
                .chapterId(request.chapterId())
                .content(request.content())
                .createdAt(Instant.now())
                .build();

        return commentRepo.save(comment);
    }

    public void delete(String commentId, String userId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("No puedes eliminar comentarios de otros usuarios");
        }

        commentRepo.deleteById(commentId);
    }

    public List<Comment> getByManga(String mangaId, String chapterId, Pageable pageable) {
        if (chapterId != null) {
            return commentRepo.findByMangaIdAndChapterId(mangaId, chapterId, pageable);
        }
        return commentRepo.findByMangaId(mangaId, pageable);
    }

    public List<Comment> getByUser(String userId) {
        return commentRepo.findByUserId(userId);
    }
}
