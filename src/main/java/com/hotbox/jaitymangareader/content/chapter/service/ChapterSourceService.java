package com.hotbox.jaitymangareader.content.chapter.service;

import com.hotbox.jaitymangareader.content.chapter.dto.AvailableSourceView;
import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;
import com.hotbox.jaitymangareader.content.chapter.repository.ChapterRepository;
import com.hotbox.jaitymangareader.content.chapter.repository.ChapterSourceRepository;
import com.hotbox.jaitymangareader.content.provider.entity.Provider;
import com.hotbox.jaitymangareader.content.provider.repository.ProviderRepository;
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
    private final ProviderRepository providerRepo;

    public ChapterSource create(UUID chapterId, UUID providerId, String lang) {
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Capítulo no encontrado"));

        Provider provider = providerRepo.findById(providerId)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        ChapterSource source = ChapterSource.builder()
                .chapter(chapter)
                .provider(provider)
                .languageCode(lang)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .isActive(true)
                .build();

        return chapterSourceRepo.save(source);
    }

    public List<ChapterSource> findSourcesByChapter(UUID chapterId) {
        Chapter chapter = chapterRepo.findById(chapterId)
                .orElseThrow(() -> new EntityNotFoundException("Capítulo no encontrado"));

        return chapterSourceRepo.findByChapter(chapter);
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
