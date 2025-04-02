package com.hotbox.jaitymangareader.content.page.service;

import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;

import com.hotbox.jaitymangareader.content.chapter.repository.ChapterSourceRepository;
import com.hotbox.jaitymangareader.content.chapter.service.ChapterService;
import com.hotbox.jaitymangareader.content.chapter.service.ChapterSourceService;
import com.hotbox.jaitymangareader.content.page.dto.PageCreateRequest;
import com.hotbox.jaitymangareader.content.page.dto.PageUpdateRequest;
import com.hotbox.jaitymangareader.content.page.entity.Page;
import com.hotbox.jaitymangareader.content.page.repository.PageRepository;
import com.hotbox.jaitymangareader.content.provider.entity.Provider;
import com.hotbox.jaitymangareader.content.provider.repository.ProviderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepo;
    //private final ChapterRepository chapterRepo;//debe ser service
    private final ChapterSourceRepository chapterSourceRepo;//este deberia ser tambien el service
    private final ProviderRepository providerRepo;//deberia ser providerService
    private final ChapterService chapterService;
    private final ChapterSourceService chapterSourceService;

    public List<Page> getByChapterSource(UUID chapterSourceId) {
        ChapterSource source = chapterSourceRepo.findById(chapterSourceId)
                .orElseThrow(() -> new EntityNotFoundException("Fuente de capítulo no encontrada"));
        return pageRepo.findByChapterSource(source);
    }

    public Page getById(UUID id) {
        return pageRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Página no encontrada"));
    }

    public Page create(UUID chapterSourceId, PageCreateRequest req) {
        ChapterSource source = chapterSourceService.findChapterSourceByChapter(chapterSourceId); // asegúrate de tenerlo inyectado

        Page page = Page.builder()
                .chapterSource(source)
                .pageNumber(req.pageNumber())
                .imageUrl(req.imageUrl())
                .build();

        return pageRepo.save(page);
    }

    public Page update(UUID id, PageUpdateRequest req) {
        Page page = getById(id);
        page.setPageNumber(req.pageNumber());
        page.setImageUrl(req.imageUrl());
        return pageRepo.save(page);
    }

    public void delete(UUID id) {
        if (!pageRepo.existsById(id)) {
            throw new EntityNotFoundException("Página no encontrada");
        }
        pageRepo.deleteById(id);
    }

    public List<Page> findByChapterAndLanguage(UUID chapterId, String languageCode, UUID optionalProviderId) {
        Chapter chapter = chapterService.getById(chapterId);
        List<ChapterSource> sources;

        if (optionalProviderId != null) {
            Provider provider = providerRepo.findById(optionalProviderId)
                    .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

            Optional<ChapterSource> specific = chapterSourceRepo.findByChapterAndProvider(chapter, provider);
            sources = specific.map(List::of).orElse(List.of());
        } else {
            sources = chapterSourceRepo.findByChapterAndLanguageCodeAndIsActiveTrue(chapter, languageCode);
        }

        if (sources.isEmpty()) {
            throw new EntityNotFoundException("No hay fuentes disponibles en ese idioma para el capítulo.");
        }

        return pageRepo.findByChapterSource(sources.get(0));
    }
}
