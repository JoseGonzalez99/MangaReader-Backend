package com.hotbox.jaitymangareader.content.manga.service;

import com.hotbox.jaitymangareader.content.manga.dto.MangaCreateRequest;
import com.hotbox.jaitymangareader.content.manga.dto.MangaUpdateRequest;
import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import com.hotbox.jaitymangareader.content.manga.repository.MangaRepository;
import com.hotbox.jaitymangareader.core.error.ApiException;
import com.hotbox.jaitymangareader.core.error.DomainErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MangaService {

    private final MangaRepository mangaRepo;


    public List<Manga> findAll(String query) {
        if (query == null || query.isBlank()) {
            return mangaRepo.findAll();
        }
        return mangaRepo.searchByTitleOrAuthor(query);
    }


    public Manga findById(UUID id) {
        return mangaRepo.findById(id)
                .orElseThrow(() -> new ApiException(DomainErrorCode.MANGA_NOT_FOUND));
    }

    public Manga create(MangaCreateRequest req) {
        Manga manga = Manga.builder()
                .title(req.title())
                .author(req.author())
                .description(req.description())
                .coverUrl(req.coverUrl())
                .faviconUrl(req.faviconUrl())
                .build();

        return mangaRepo.save(manga);
    }

    public Manga update(UUID id, MangaUpdateRequest req) {
        Manga manga = findById(id);
        manga.setTitle(req.title());
        manga.setAuthor(req.author());
        manga.setDescription(req.description());
        manga.setCoverUrl(req.coverUrl());
        manga.setFaviconUrl(req.faviconUrl());
        return mangaRepo.save(manga);
    }

    public void delete(UUID id) {
        if (!mangaRepo.existsById(id)) {
            throw new ApiException(DomainErrorCode.MANGA_NOT_FOUND);
        }
        mangaRepo.deleteById(id);
    }
}
