package com.hotbox.jaitymangareader.content.page.controller;

import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.chapter.service.ChapterService;
import com.hotbox.jaitymangareader.content.page.entity.Page;
import com.hotbox.jaitymangareader.content.page.service.PageService;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import com.hotbox.jaitymangareader.user.service.UserContextService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/pages")
@RequiredArgsConstructor
public class PublicPageController {

    private final PageService pageService;
    private final ChapterService chapterService;
    private final UserContextService userContextService;

    @GetMapping("/by-chapter/{chapterId}")
    public ResponseEntity<?> getPagesByLanguage(
            @PathVariable UUID chapterId,
            @RequestParam @NotBlank @Size(max = 10) String lang,
            @RequestParam(required = false) UUID providerId,
            Authentication auth,
            HttpServletRequest request) {

        List<Page> pages = pageService.findByChapterAndLanguage(chapterId, lang, providerId);

        // Tracking de lectura
        if (auth != null && auth.isAuthenticated()) {
            Chapter chapter = chapterService.getById(chapterId);
            UUID mangaId = chapter.getVolume().getManga().getId();

            userContextService.recordReadingProgress(
                    auth.getName(),
                    mangaId.toString(),
                    chapterId.toString(),
                    1 // asumimos que empieza por la página 1
            );
        }

        return ResponseUtil.success(pages, "Páginas obtenidas correctamente", request);
    }
}
