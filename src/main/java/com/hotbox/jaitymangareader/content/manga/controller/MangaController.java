package com.hotbox.jaitymangareader.content.manga.controller;

import com.hotbox.jaitymangareader.content.manga.dto.MangaCreateRequest;
import com.hotbox.jaitymangareader.content.manga.dto.MangaPublicView;
import com.hotbox.jaitymangareader.content.manga.dto.MangaUpdateRequest;
import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import com.hotbox.jaitymangareader.content.manga.service.MangaService;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MangaController {

    private final MangaService mangaService;

    @GetMapping("/mangas")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        List<Manga> all = mangaService.findAll();
        List<MangaPublicView> views = all.stream()
                .map(MangaPublicView::from)
                .toList();
        return ResponseUtil.success(views, "Mangas cargados correctamente", request);
    }

    @GetMapping("/mangas/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id, HttpServletRequest request) {
        MangaPublicView view = MangaPublicView.from(mangaService.findById(id));
        return ResponseUtil.success(view, "Manga encontrado", request);
    }

    @PostMapping("/admin/mangas")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> create(
            @RequestBody @Valid MangaCreateRequest req,
            HttpServletRequest request) {
        Manga created = mangaService.create(req);
        return ResponseUtil.created(created, "Manga creado correctamente", request);
    }

    @PutMapping("/admin/mangas/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody @Valid MangaUpdateRequest req,
            HttpServletRequest request) {
        Manga updated = mangaService.update(id, req);
        return ResponseUtil.success(updated, "Manga actualizado correctamente", request);
    }

    @DeleteMapping("/admin/mangas/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {
        mangaService.delete(id);
        return ResponseUtil.noContent("Manga eliminado", request);
    }
}
