package com.hotbox.jaitymangareader.user.controller;

import com.hotbox.jaitymangareader.core.error.ApiException;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import com.hotbox.jaitymangareader.user.dto.ReadingEntryView;
import com.hotbox.jaitymangareader.user.dto.UserPreferencesRequest;
import com.hotbox.jaitymangareader.user.entity.Preferences;
import com.hotbox.jaitymangareader.user.entity.UserContext;
import com.hotbox.jaitymangareader.user.service.UserContextService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.hotbox.jaitymangareader.core.error.DomainErrorCode.*;

@RestController
@RequestMapping("/api/v1/me/context")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENT') or hasRole('STAFF')")
public class UserContextController {

    private final UserContextService contextService;

    @GetMapping("/preferences")
    public ResponseEntity<?> getPreferences(Principal auth, HttpServletRequest req) {
        UserContext ctx = contextService.getByUserId(auth.getName());
        return ResponseUtil.success(ctx.getPreferences(), "Preferencias cargadas", req);
    }

    @PutMapping("/preferences")
    public ResponseEntity<?> updatePreferences(
            Principal auth,
            @RequestBody @Valid UserPreferencesRequest request,
            HttpServletRequest req) {

        UserContext ctx = contextService.getByUserId(auth.getName());

        Preferences prefs = ctx.getPreferences();
        prefs.setTheme(request.theme());
        prefs.setReadingDirection(request.readingDirection());
        prefs.setDefaultProvider(request.defaultProvider());

        contextService.update(auth.getName(), ctx);

        return ResponseUtil.success(prefs, "Preferencias actualizadas", req);
    }

    @GetMapping("/last-read")
    public ResponseEntity<?> lastRead(Principal auth, HttpServletRequest req) {
        UserContext ctx = contextService.getByUserId(auth.getName());

        if (ctx.getLastRead() == null) {
            throw new ApiException(USER_NO_LAST_READ);
        }

        return ResponseUtil.success(ctx.getLastRead(), "Última lectura cargada", req);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(Principal auth, HttpServletRequest req) {
        UserContext ctx = contextService.getByUserId(auth.getName());

        if (ctx.getReadingHistory() == null || ctx.getReadingHistory().isEmpty()) {
            throw new ApiException(USER_NO_READING_HISTORY);
        }

        List<ReadingEntryView> views = ctx.getReadingHistory().stream()
                .map(ReadingEntryView::from)
                .toList();

        return ResponseUtil.success(views, "Historial de lectura", req);
    }
}
