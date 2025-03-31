package com.hotbox.jaitymangareader.content.manga.repository;

import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MangaRepository extends JpaRepository<Manga, UUID> {
    boolean existsByTitleIgnoreCase(String title);
}
