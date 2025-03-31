package com.hotbox.jaitymangareader.content.chapter.controller;

import com.hotbox.jaitymangareader.content.chapter.dto.AvailableSourceView;
import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;
import com.hotbox.jaitymangareader.content.chapter.service.ChapterSourceService;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/chapter-sources")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class ChapterSourceController {

    private final ChapterSourceService chapterSourceService;

    @PostMapping
    public ResponseEntity<?> create(
            @RequestParam UUID chapterId,
            @RequestParam UUID providerId,
            @RequestParam @NotBlank @Size(max = 10) String lang,
            HttpServletRequest request) {

        ChapterSource created = chapterSourceService.create(chapterId, providerId, lang);
        return ResponseUtil.created(created, "Fuente de capítulo registrada", request);
    }

    @GetMapping("/by-chapter/{chapterId}")
    public ResponseEntity<?> listByChapter(
            @PathVariable UUID chapterId,
            @RequestParam(defaultValue = "false") boolean view,
            HttpServletRequest request) {

        if (view) {
            List<AvailableSourceView> views = chapterSourceService.getAvailableViews(chapterId);
            return ResponseUtil.success(views, "Vista simplificada de fuentes", request);
        }

        List<ChapterSource> list = chapterSourceService.findSourcesByChapter(chapterId);
        return ResponseUtil.success(list, "Fuentes del capítulo obtenidas", request);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> toggle(
            @PathVariable UUID id,
            @RequestParam boolean enable,
            HttpServletRequest request) {

        chapterSourceService.toggleActive(id, enable);
        return ResponseUtil.noContent("Estado de la fuente actualizado", request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {

        chapterSourceService.delete(id);
        return ResponseUtil.noContent("Fuente eliminada", request);
    }
}
