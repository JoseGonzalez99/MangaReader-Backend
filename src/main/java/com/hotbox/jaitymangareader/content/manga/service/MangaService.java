package com.hotbox.jaitymangareader.content.manga.service;

import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import com.hotbox.jaitymangareader.content.manga.repository.MangaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MangaService {

    private final MangaRepository mangaRepo;

    public List<Manga> findAll() {
        return mangaRepo.findAll();
    }

    public Manga findById(UUID id) {
        return mangaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manga no encontrado"));
    }

    public Manga create(Manga manga) {
        return mangaRepo.save(manga);
    }

    public void delete(UUID id) {
        if (!mangaRepo.existsById(id)) {
            throw new EntityNotFoundException("Manga no encontrado");
        }
        mangaRepo.deleteById(id);
    }
}
