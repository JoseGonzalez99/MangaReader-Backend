package com.hotbox.jaitymangareader.content.chapter.service;

import com.hotbox.jaitymangareader.content.chapter.dto.AvailableSourceView;
import com.hotbox.jaitymangareader.content.chapter.dto.ChapterSourceCreateRequest;
import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;
import com.hotbox.jaitymangareader.content.chapter.repository.ChapterRepository;
import com.hotbox.jaitymangareader.content.chapter.repository.ChapterSourceRepository;
import com.hotbox.jaitymangareader.content.provider.entity.Provider;
import com.hotbox.jaitymangareader.content.provider.service.ProviderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChapterSourceService {

    private final ChapterSourceRepository chapterSourceRepo;
    private final ChapterRepository chapterRepo;

    private final ChapterService chapterService;
    private final ProviderService providerService;

    public ChapterSource create(ChapterSourceCreateRequest req) {
        Chapter chapter = chapterService.getById(req.chapterId());
        Provider provider = providerService.getProviderById(req.providerId());

        ChapterSource source = ChapterSource.builder()
                .chapter(chapter)
                .provider(provider)
                .languageCode(req.languageCode())
                .isActive(true)
                .build();

        return chapterSourceRepo.save(source);
    }

    public List<ChapterSource> findSourcesByChapter(UUID chapterId) {
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Capítulo no encontrado"));

        return chapterSourceRepo.findByChapter(chapter);
    }


    public ChapterSource findChapterSourceByChapter(UUID chapterSourceId) {

        return chapterSourceRepo.findById(chapterSourceId)
                .orElseThrow(() -> new EntityNotFoundException("Fuente de capítulo no encontrada"));
    }




    public void toggleActive(UUID sourceId, boolean enable) {
        ChapterSource src = chapterSourceRepo.findById(sourceId)
                .orElseThrow(() -> new EntityNotFoundException("Fuente no encontrada"));

        src.setActive(enable);
        src.setUpdatedAt(Instant.now());
        chapterSourceRepo.save(src);
    }

    public void delete(UUID sourceId) {
        if (!chapterSourceRepo.existsById(sourceId)) {
            throw new EntityNotFoundException("Fuente no encontrada");
        }
        chapterSourceRepo.deleteById(sourceId);
    }
    public List<ChapterSource> getAvailableSources(UUID chapterId) {
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Capítulo no encontrado"));

        return chapterSourceRepo.findByChapterAndIsActiveTrue(chapter);
    }

    public List<AvailableSourceView> getAvailableViews(UUID chapterId) {
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Capítulo no encontrado"));

        return chapterSourceRepo.findByChapter(chapter).stream()
                .map(cs -> new AvailableSourceView(
                        cs.getId(),
                        cs.getLanguageCode(),
                        cs.getProvider().getProviderName(),
                        cs.getProvider().getLogoUrl(),
                        cs.isActive()
                ))
                .toList();
    }

}
