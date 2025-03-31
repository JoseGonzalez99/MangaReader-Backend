package com.hotbox.jaitymangareader.content.chapter.controller;

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
@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
public class ChapterSourcePublicController {

    private final ChapterSourceService chapterSourceService;

    @GetMapping("/{chapterId}/available-sources")
    public ResponseEntity<?> getAvailableSources(
            @PathVariable UUID chapterId,
            HttpServletRequest request) {

        List<ChapterSource> sources = chapterSourceService.getAvailableSources(chapterId);
        return ResponseUtil.success(sources, "Fuentes disponibles encontradas", request);
    }
}
