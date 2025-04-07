package com.hotbox.jaitymangareader.content.chapter.service;

import com.hotbox.jaitymangareader.content.chapter.dto.ChapterCreateRequest;
import com.hotbox.jaitymangareader.content.chapter.dto.ChapterUpdateRequest;
import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.repository.ChapterRepository;
import com.hotbox.jaitymangareader.content.volume.entity.Volume;
import com.hotbox.jaitymangareader.content.volume.repository.VolumeRepository;
import com.hotbox.jaitymangareader.content.volume.service.VolumeService;
import com.hotbox.jaitymangareader.core.error.ApiException;
import com.hotbox.jaitymangareader.core.error.DomainErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepo;

    private final VolumeService volumeService;

    public List<Chapter> getByVolume(UUID volumeId) {

        Volume volume = volumeService.getById(volumeId);

        return chapterRepo.findByVolume(volume);
    }

    public Chapter getById(UUID id) {
        return chapterRepo.findById(id)
                .orElseThrow(() -> new ApiException(DomainErrorCode.CHAPTER_NOT_FOUND));
    }



    public Chapter create(UUID volumeId, ChapterCreateRequest req) {
        Volume volume = volumeService.getById(volumeId); // asegúrate de tener VolumeService inyectado
        Chapter chapter = Chapter.builder()
                .chapterNumber(req.chapterNumber())
                .title(req.title())
                .volume(volume)
                .build();
        return chapterRepo.save(chapter);
    }
    public Chapter update(UUID id, ChapterUpdateRequest req) {
        Chapter chapter = getById(id);
        chapter.setChapterNumber(req.chapterNumber());
        chapter.setTitle(req.title());
        return chapterRepo.save(chapter);
    }

    public void delete(UUID id) {
        if (!chapterRepo.existsById(id)) {
            throw new ApiException(DomainErrorCode.CHAPTER_NOT_FOUND);
        }
        chapterRepo.deleteById(id);
    }
}
