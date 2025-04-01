package com.hotbox.jaitymangareader.content.manga.controller;

import com.hotbox.jaitymangareader.content.chapter.dto.AvailableSourceView;
import com.hotbox.jaitymangareader.content.manga.dto.MangaPublicView;
import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import com.hotbox.jaitymangareader.content.manga.service.MangaService;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MangaController {

    private static final Logger log = LoggerFactory.getLogger(MangaController.class);
    private final MangaService mangaService;

    @GetMapping("/mangas")
    public ResponseEntity<?> listAll(HttpServletRequest request) {
        List<Manga> mangas = mangaService.findAll();
        List<MangaPublicView> views = mangas.stream()
                .map(MangaPublicView::from)
                .toList();

        log.info("obteneindo todo");
        return ResponseUtil.success(views, "Mangas obtenidos correctamente", request);
    }


    @GetMapping("/mangas/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id, HttpServletRequest request) {
        return ResponseUtil.success(mangaService.findById(id), "Manga encontrado", request);
    }

    @PostMapping("/admin/mangas")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> create(@RequestBody @Valid Manga manga, HttpServletRequest request) {
        Manga created = mangaService.create(manga);
        return ResponseUtil.created(created, "Manga creado correctamente", request);
    }

    @DeleteMapping("/admin/mangas/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> delete(@PathVariable UUID id, HttpServletRequest request) {
        mangaService.delete(id);
        return ResponseUtil.noContent("Manga eliminado correctamente", request);
    }
}
