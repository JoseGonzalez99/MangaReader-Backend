package com.hotbox.jaitymangareader.content.chapter.controller;

import com.hotbox.jaitymangareader.content.chapter.dto.AvailableSourceView;
import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;
import com.hotbox.jaitymangareader.content.chapter.service.ChapterSourceService;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/sources")
@RequiredArgsConstructor
public class ChapterSourcePublicController {

    private final ChapterSourceService chapterSourceService;

    @GetMapping("/{chapterId}/available-sources")
    public ResponseEntity<?> getAvailableSources(@PathVariable UUID chapterId, HttpServletRequest req) {
        List<ChapterSource> sources = chapterSourceService.getAvailableSources(chapterId);
        List<AvailableSourceView> views = sources.stream()
                .map(AvailableSourceView::from)
                .toList();
        return ResponseUtil.success(views, "Fuentes disponibles encontradas", req);
    }

}
