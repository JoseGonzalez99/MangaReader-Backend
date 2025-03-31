package com.hotbox.jaitymangareader.content.chapter.repository;

import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;
import com.hotbox.jaitymangareader.content.provider.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChapterSourceRepository extends JpaRepository<ChapterSource, UUID> {

    List<ChapterSource> findByChapter(Chapter chapter);

    List<ChapterSource> findByChapterAndLanguageCodeAndIsActiveTrue(Chapter chapter, String languageCode);

    Optional<ChapterSource> findByChapterAndProvider(Chapter chapter, Provider provider);

    List<ChapterSource> findByChapterAndIsActiveTrue(Chapter chapter);
}
