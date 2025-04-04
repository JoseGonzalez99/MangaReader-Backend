package com.hotbox.jaitymangareader.user.controller;

import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import com.hotbox.jaitymangareader.user.entity.UserContext;
import com.hotbox.jaitymangareader.user.service.UserContextService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me/context")
@RequiredArgsConstructor
public class UserContextController {

    private final UserContextService contextService;

    @GetMapping
    public ResponseEntity<?> get(Authentication auth, HttpServletRequest req) {
        String userId = auth.getName(); // normalmente el email
        UserContext ctx = contextService.getByUserId(userId);
        return ResponseUtil.success(ctx, "Contexto cargado correctamente", req);
    }

    @PutMapping
    public ResponseEntity<?> update(
            Authentication auth,
            @RequestBody @Valid UserContext updated,
            HttpServletRequest req) {

        String userId = auth.getName();
        UserContext saved = contextService.update(userId, updated);
        return ResponseUtil.success(saved, "Contexto actualizado correctamente", req);
    }
}
