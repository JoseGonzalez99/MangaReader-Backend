package com.hotbox.jaitymangareader.controller;


import com.hotbox.jaitymangareader.service.RefreshTokenService;
import com.hotbox.jaitymangareader.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/me/sessions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENT') or hasRole('STAFF')")
public class ClientSessionController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping
    public ResponseEntity<?> listMySessions(Authentication auth, HttpServletRequest request) {
        String userId = auth.getName();
        var sessions = refreshTokenService.findUserSessions(userId);
        return ResponseUtil.success(sessions, "Sesiones activas obtenidas correctamente", request);
    }

    @DeleteMapping("/{tokenId}")
    public ResponseEntity<?> revokeSession(
            @PathVariable @NotBlank String tokenId,
            Authentication auth,
            HttpServletRequest request) {

        String userId = auth.getName();

        boolean revoked = refreshTokenService.revokeTokenById(userId, tokenId);

        if (!revoked) {
            return ResponseUtil.success(null, "Token no encontrado o no pertenece al usuario", request);
        }

        return ResponseUtil.success(null, "Sesión revocada correctamente", request);
    }
}

