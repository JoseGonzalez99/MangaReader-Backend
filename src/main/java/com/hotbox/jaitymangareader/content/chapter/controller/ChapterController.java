package com.hotbox.jaitymangareader.content.chapter.controller;

import com.hotbox.jaitymangareader.content.chapter.dto.ChapterCreateRequest;
import com.hotbox.jaitymangareader.content.chapter.dto.ChapterPublicView;
import com.hotbox.jaitymangareader.content.chapter.dto.ChapterUpdateRequest;
import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.service.ChapterService;
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
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/volumes/{volumeId}/chapters")
    public ResponseEntity<?> getByVolume(
            @PathVariable UUID volumeId,
            HttpServletRequest req) {

        List<Chapter> chapters = chapterService.getByVolume(volumeId);
        List<ChapterPublicView> views = chapters.stream()
                .map(ChapterPublicView::from)
                .toList();
        return ResponseUtil.success(views, "Capítulos encontrados", req);
    }

    @GetMapping("/chapters/{id}")
    public ResponseEntity<?> getById(
            @PathVariable UUID id,
            HttpServletRequest req) {

        ChapterPublicView view = ChapterPublicView.from(chapterService.getById(id));
        return ResponseUtil.success(view, "Capítulo encontrado", req);
    }











    @PostMapping("/admin/volumes/{volumeId}/chapters")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> create(
            @PathVariable UUID volumeId,
            @RequestBody @Valid ChapterCreateRequest req,
            HttpServletRequest httpReq) {

        Chapter created = chapterService.create(volumeId, req);
        ChapterPublicView view = ChapterPublicView.from(created);
        return ResponseUtil.created(view, "Capítulo creado correctamente", httpReq);
    }

    @PutMapping("/admin/chapters/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody @Valid ChapterUpdateRequest req,
            HttpServletRequest httpReq) {

        Chapter updated = chapterService.update(id, req);
        ChapterPublicView view = ChapterPublicView.from(updated);
        return ResponseUtil.success(view, "Capítulo actualizado", httpReq);
    }

    @DeleteMapping("/admin/chapters/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest req) {

        chapterService.delete(id);
        return ResponseUtil.noContent("Capítulo eliminado", req);
    }
}
