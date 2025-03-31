package com.hotbox.jaitymangareader.content.page.repository;

import com.hotbox.jaitymangareader.content.page.entity.Page;
import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PageRepository extends JpaRepository<Page, UUID> {

    List<Page> findByChapterSource(ChapterSource chapterSource);
}
