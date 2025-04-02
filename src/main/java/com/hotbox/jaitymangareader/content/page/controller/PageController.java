package com.hotbox.jaitymangareader.content.page.controller;

import com.hotbox.jaitymangareader.content.page.dto.PageCreateRequest;
import com.hotbox.jaitymangareader.content.page.dto.PagePublicView;
import com.hotbox.jaitymangareader.content.page.dto.PageUpdateRequest;
import com.hotbox.jaitymangareader.content.page.entity.Page;
import com.hotbox.jaitymangareader.content.page.service.PageService;
import com.hotbox.jaitymangareader.content.volume.dto.VolumePublicView;
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
        List<PagePublicView> view =  pages.stream().map( PagePublicView::from).toList();

        return ResponseUtil.success(view, "Páginas encontradas", request);
    }

    @PostMapping("/{chapterSourceId}")
    public ResponseEntity<?> create(
            @PathVariable UUID chapterSourceId,
            @RequestBody @Valid PageCreateRequest req,
            HttpServletRequest request) {

        Page created = pageService.create(chapterSourceId, req);
        PagePublicView view = PagePublicView.from(created);
        return ResponseUtil.created(view, "Página agregada", request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody @Valid PageUpdateRequest req,
            HttpServletRequest request) {

        Page updated = pageService.update(id, req);
        PagePublicView view = PagePublicView.from(updated);
        return ResponseUtil.success(view, "Página actualizada", request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {

        pageService.delete(id);
        return ResponseUtil.noContent("Página eliminada", request);
    }
}
