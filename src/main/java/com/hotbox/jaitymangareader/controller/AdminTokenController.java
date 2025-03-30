package com.hotbox.jaitymangareader.controller;

import com.hotbox.jaitymangareader.dto.RefreshTokenView;
import com.hotbox.jaitymangareader.repository.RefreshTokenRepository;
import com.hotbox.jaitymangareader.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/tokens")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class AdminTokenController {

    private final RefreshTokenRepository refreshRepo;

    @GetMapping("/active")
    public ResponseEntity<?> getActiveTokens(HttpServletRequest request) {
        List<RefreshTokenView> tokens = refreshRepo.findAll().stream()
                .filter(t -> !t.isRevoked())
                .map(t -> new RefreshTokenView(
                        t.getId(),
                        t.getUserId(),
                        t.getToken(),
                        t.getExpiryDate(),
                        t.isRevoked(),
                        t.getIpAddress(),
                        t.getUserAgent()
                ))
                .toList();

        return ResponseUtil.success(tokens, "Tokens activos recuperados correctamente", request);
    }
}

