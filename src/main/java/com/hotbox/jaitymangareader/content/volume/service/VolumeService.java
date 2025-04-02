package com.hotbox.jaitymangareader.content.volume.service;

import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import com.hotbox.jaitymangareader.content.manga.repository.MangaRepository;
import com.hotbox.jaitymangareader.content.volume.dto.VolumeCreateRequest;
import com.hotbox.jaitymangareader.content.volume.dto.VolumeUpdateRequest;
import com.hotbox.jaitymangareader.content.volume.entity.Volume;
import com.hotbox.jaitymangareader.content.volume.repository.VolumeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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



    public Manga getManga(UUID id) {
        return mangaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manga no encontrado"));
    }


    public Volume getById(UUID id) {
        return volumeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Volumen no encontrado"));
    }

    public Volume create(UUID mangaId, VolumeCreateRequest req) {
        Manga manga = getManga(mangaId); // si aún no tenés mangaService, lo agregamos

        Volume volume = Volume.builder()
                .volumeNumber(req.volumeNumber())
                .title(req.title())
                .coverUrl(req.coverUrl())
                .manga(manga)
                .build();

        return volumeRepo.save(volume);
    }

    public Volume update(UUID id, VolumeUpdateRequest req) {
        Volume volume = getById(id);
        volume.setVolumeNumber(req.volumeNumber());
        volume.setTitle(req.title());
        volume.setCoverUrl(req.coverUrl());
        return volumeRepo.save(volume);
    }

    public void delete(UUID id) {
        if (!volumeRepo.existsById(id)) {
            throw new EntityNotFoundException("Volumen no encontrado");
        }
        volumeRepo.deleteById(id);
    }
}
