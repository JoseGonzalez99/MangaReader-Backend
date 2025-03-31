package com.hotbox.jaitymangareader.content.page.controller;

import com.hotbox.jaitymangareader.content.page.entity.Page;
import com.hotbox.jaitymangareader.content.page.service.PageService;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pages")
@RequiredArgsConstructor
public class PublicPageController {

    private final PageService pageService;

    @GetMapping("/by-chapter/{chapterId}")
    public ResponseEntity<?> getPagesByLanguage(
            @PathVariable UUID chapterId,
            @RequestParam @NotBlank @Size(max = 10) String lang,
            @RequestParam(required = false) UUID providerId,
            HttpServletRequest request) {

        List<Page> pages = pageService.findByChapterAndLanguage(chapterId, lang, providerId);
        return ResponseUtil.success(pages, "Páginas obtenidas correctamente", request);
    }
}
