package com.hotbox.jaitymangareader.content.page.controller;

import com.hotbox.jaitymangareader.content.page.entity.Page;
import com.hotbox.jaitymangareader.content.page.service.PageService;
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
@RequestMapping("/api/v1/admin/pages")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class PageController {

    private final PageService pageService;

    @GetMapping("/by-source/{chapterSourceId}")
    public ResponseEntity<?> getPages(
            @PathVariable UUID chapterSourceId,
            HttpServletRequest request) {

        List<Page> pages = pageService.getByChapterSource(chapterSourceId);
        return ResponseUtil.success(pages, "Páginas encontradas", request);
    }

    @PostMapping("/{chapterSourceId}")
    public ResponseEntity<?> create(
            @PathVariable UUID chapterSourceId,
            @RequestBody @Valid Page page,
            HttpServletRequest request) {

        Page created = pageService.create(chapterSourceId, page);
        return ResponseUtil.created(created, "Página agregada", request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {
        pageService.delete(id);
        return ResponseUtil.noContent("Página eliminada", request);
    }
}
