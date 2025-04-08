package com.hotbox.jaitymangareader.content.chapter.controller;

import com.hotbox.jaitymangareader.content.chapter.dto.ChapterPublicView;
import com.hotbox.jaitymangareader.content.chapter.service.ChapterService;

import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/chapters")
@RequiredArgsConstructor
public class PublicChapterController {

    private final ChapterService chapterService;


    @GetMapping("/by-manga/{mangaId}")
    public ResponseEntity<?>getChaptersByManga(
            @PathVariable UUID mangaId,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) UUID providerId,
            HttpServletRequest req
    ) {
        List<ChapterPublicView> views;
        if (language != null && providerId != null) {
            views= chapterService.getChaptersByMangaIdLanguageAndProvider(mangaId, language, providerId);
        } else if (language != null) {
            views= chapterService.getChaptersByMangaIdAndLanguage(mangaId, language);
        } else {
            views= chapterService.getChaptersByMangaId(mangaId);
        }

        return ResponseUtil.success(views, "Capítulos encontrados", req);
    }


}
