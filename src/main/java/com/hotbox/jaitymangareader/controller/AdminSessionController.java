package com.hotbox.jaitymangareader.controller;

import com.hotbox.jaitymangareader.service.RefreshTokenService;
import com.hotbox.jaitymangareader.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/sessions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class AdminSessionController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserSessions(
            @PathVariable @NotBlank String userId,
            HttpServletRequest request) {

        var sessions = refreshTokenService.findUserSessions(userId);
        return ResponseUtil.success(sessions, "Sesiones activas del usuario obtenidas correctamente", request);
    }

    @DeleteMapping("/{userId}/{tokenId}")
    public ResponseEntity<?> revokeUserToken(
            @PathVariable @NotBlank String userId,
            @PathVariable @NotBlank String tokenId,
            HttpServletRequest request) {

        boolean revoked = refreshTokenService.revokeTokenById(userId, tokenId);

        if (!revoked) {
            // Aquí podrías lanzar una ApiException si querés estandarizar también el error
            return ResponseUtil.success(null, "No se pudo revocar el token (ya estaba inactivo o no existe)", request);
        }

        return ResponseUtil.success(null, "Token revocado correctamente", request);
    }
}
