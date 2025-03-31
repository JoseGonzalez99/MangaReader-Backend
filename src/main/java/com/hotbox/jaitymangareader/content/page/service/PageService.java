package com.hotbox.jaitymangareader.content.page.service;

import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;
import com.hotbox.jaitymangareader.content.chapter.repository.ChapterRepository;
import com.hotbox.jaitymangareader.content.chapter.repository.ChapterSourceRepository;
import com.hotbox.jaitymangareader.content.page.entity.Page;
import com.hotbox.jaitymangareader.content.page.repository.PageRepository;
import com.hotbox.jaitymangareader.content.provider.entity.Provider;
import com.hotbox.jaitymangareader.content.provider.repository.ProviderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepo;
    private final ChapterRepository chapterRepo;
    private final ChapterSourceRepository chapterSourceRepo;
    private final ProviderRepository providerRepo;

    public List<Page> getByChapterSource(UUID chapterSourceId) {
        ChapterSource source = chapterSourceRepo.findById(chapterSourceId)
                .orElseThrow(() -> new EntityNotFoundException("Fuente de capítulo no encontrada"));
        return pageRepo.findByChapterSource(source);
    }

    public Page getById(UUID id) {
        return pageRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Página no encontrada"));
    }

    public Page create(UUID chapterSourceId, Page page) {
        ChapterSource chapterSource = chapterSourceRepo.findById(chapterSourceId)
                .orElseThrow(() -> new EntityNotFoundException("Fuente de capítulo no encontrada"));

        page.setChapterSource(chapterSource);
        page.setCreatedAt(Instant.now());
        page.setUpdatedAt(Instant.now());

        return pageRepo.save(page);
    }

    public void delete(UUID id) {
        if (!pageRepo.existsById(id)) {
            throw new EntityNotFoundException("Página no encontrada");
        }
        pageRepo.deleteById(id);
    }

    public List<Page> findByChapterAndLanguage(UUID chapterId, String languageCode, UUID optionalProviderId) {
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Capítulo no encontrado"));

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
