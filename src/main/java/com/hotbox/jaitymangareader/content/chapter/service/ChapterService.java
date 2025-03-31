package com.hotbox.jaitymangareader.content.chapter.service;

import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.repository.ChapterRepository;
import com.hotbox.jaitymangareader.content.volume.entity.Volume;
import com.hotbox.jaitymangareader.content.volume.repository.VolumeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepo;
    private final VolumeRepository volumeRepo;

    public List<Chapter> getByVolume(UUID volumeId) {
        Volume volume = volumeRepo.findById(volumeId)
                .orElseThrow(() -> new EntityNotFoundException("Volumen no encontrado"));
        return chapterRepo.findByVolume(volume);
    }

    public Chapter getById(UUID id) {
        return chapterRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Capítulo no encontrado"));
    }

    public Chapter create(UUID volumeId, Chapter chapter) {
        Volume volume = volumeRepo.findById(volumeId)
                .orElseThrow(() -> new EntityNotFoundException("Volumen no encontrado"));

        chapter.setVolume(volume);
        return chapterRepo.save(chapter);
    }

    public void delete(UUID id) {
        if (!chapterRepo.existsById(id)) {
            throw new EntityNotFoundException("Capítulo no encontrado");
        }
        chapterRepo.deleteById(id);
    }
}
