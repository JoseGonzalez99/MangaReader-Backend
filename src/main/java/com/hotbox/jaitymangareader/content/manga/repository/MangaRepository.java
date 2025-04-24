package com.hotbox.jaitymangareader.content.manga.repository;

import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MangaRepository extends JpaRepository<Manga, UUID> {
    boolean existsByTitleIgnoreCase(String title);

    @Query("SELECT m FROM Manga m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(m.author) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Manga> searchByTitleOrAuthor(@Param("query") String query);
}
