package com.hotbox.jaitymangareader.content.volume.service;

import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import com.hotbox.jaitymangareader.content.manga.repository.MangaRepository;
import com.hotbox.jaitymangareader.content.volume.entity.Volume;
import com.hotbox.jaitymangareader.content.volume.repository.VolumeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VolumeService {

    private final VolumeRepository volumeRepo;
    private final MangaRepository mangaRepo;

    public List<Volume> getByManga(UUID mangaId) {
        Manga manga = mangaRepo.findById(mangaId)
                .orElseThrow(() -> new EntityNotFoundException("Manga no encontrado"));
        return volumeRepo.findByManga(manga);
    }

    public Volume getById(UUID id) {
        return volumeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Volumen no encontrado"));
    }

    public Volume create(UUID mangaId, Volume volume) {
        Manga manga = mangaRepo.findById(mangaId)
                .orElseThrow(() -> new EntityNotFoundException("Manga no encontrado"));

        volume.setManga(manga);
        return volumeRepo.save(volume);
    }

    public void delete(UUID id) {
        if (!volumeRepo.existsById(id)) {
            throw new EntityNotFoundException("Volumen no encontrado");
        }
        volumeRepo.deleteById(id);
    }
}
