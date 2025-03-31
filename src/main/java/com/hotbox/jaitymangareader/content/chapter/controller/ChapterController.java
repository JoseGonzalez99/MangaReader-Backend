package com.hotbox.jaitymangareader.content.chapter.controller;

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
    public ResponseEntity<?> listByVolume(
            @PathVariable UUID volumeId,
            HttpServletRequest request) {

        List<Chapter> chapters = chapterService.getByVolume(volumeId);
        return ResponseUtil.success(chapters, "Capítulos encontrados", request);
    }

    @GetMapping("/chapters/{id}")
    public ResponseEntity<?> getChapter(
            @PathVariable UUID id,
            HttpServletRequest request) {

        return ResponseUtil.success(chapterService.getById(id), "Capítulo encontrado", request);
    }

    @PostMapping("/admin/volumes/{volumeId}/chapters")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> create(
            @PathVariable UUID volumeId,
            @RequestBody @Valid Chapter chapter,
            HttpServletRequest request) {

        Chapter created = chapterService.create(volumeId, chapter);
        return ResponseUtil.created(created, "Capítulo creado correctamente", request);
    }

    @DeleteMapping("/admin/chapters/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {

        chapterService.delete(id);
        return ResponseUtil.noContent("Capítulo eliminado", request);
    }
}
