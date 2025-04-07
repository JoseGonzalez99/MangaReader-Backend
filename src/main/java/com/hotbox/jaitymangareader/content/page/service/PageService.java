package com.hotbox.jaitymangareader.content.page.service;

import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;
import com.hotbox.jaitymangareader.content.chapter.repository.ChapterSourceRepository;
import com.hotbox.jaitymangareader.content.chapter.service.ChapterService;
import com.hotbox.jaitymangareader.content.page.dto.PageCreateRequest;
import com.hotbox.jaitymangareader.content.page.dto.PageUpdateRequest;
import com.hotbox.jaitymangareader.content.page.entity.Page;
import com.hotbox.jaitymangareader.content.page.repository.PageRepository;
import com.hotbox.jaitymangareader.core.error.ApiException;
import com.hotbox.jaitymangareader.core.error.DomainErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final ChapterSourceRepository chapterSourceRepository;
    private final ChapterService chapterService;

    public List<Page> getByChapterSource(UUID chapterSourceId) {
        ChapterSource source = chapterSourceRepository.findById(chapterSourceId)
                .orElseThrow(() -> new ApiException(DomainErrorCode.CHAPTER_SOURCE_NOT_FOUND));

        return pageRepository.findByChapterSource(source);
    }

    public Page update(UUID id, PageUpdateRequest req) {
        Page page = getById(id);
        page.setPageNumber(req.pageNumber());
        page.setImageUrl(req.imageUrl());
        return pageRepository.save(page);
    }


    public Page create(UUID chapterSourceId, PageCreateRequest req) {
        ChapterSource source = chapterSourceRepository.findById(chapterSourceId)
                .orElseThrow(() -> new ApiException(DomainErrorCode.CHAPTER_SOURCE_NOT_FOUND));

        Page page = new Page();
        page.setImageUrl(req.imageUrl());
        page.setPageNumber(req.pageNumber());
        page.setChapterSource(source);
        return pageRepository.save(page);
    }

    public void delete(UUID id) {
        pageRepository.deleteById(id);
    }

    public Page getById(UUID id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new ApiException(DomainErrorCode.PAGE_NOT_FOUND));
    }

    public List<Page> findByChapterAndLanguage(UUID chapterId, String lang, UUID providerId) {
        Chapter chapter = chapterService.getById(chapterId);
        List<ChapterSource> sources = chapterSourceRepository
                .findByChapterAndLanguageCode(chapter, lang);

        ChapterSource source = sources.stream()
                .filter(s -> providerId == null || s.getProvider().getId().equals(providerId))
                .findFirst()
                .orElseThrow(() ->new ApiException(DomainErrorCode.CHAPTER_SOURCE_NOT_FOUND));

        return pageRepository.findByChapterSource(source);
    }
}
