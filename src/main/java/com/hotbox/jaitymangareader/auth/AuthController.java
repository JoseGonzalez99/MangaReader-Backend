package com.hotbox.jaitymangareader.auth;

import com.hotbox.jaitymangareader.auth.dto.LoginRequest;
import com.hotbox.jaitymangareader.audit.dto.RefreshRequest;
import com.hotbox.jaitymangareader.auth.dto.RegisterRequest;
import com.hotbox.jaitymangareader.auth.dto.TokenResponse;
import com.hotbox.jaitymangareader.config.security.JwtUtils;
import com.hotbox.jaitymangareader.user.entity.AppUser;
import com.hotbox.jaitymangareader.auth.entity.RefreshToken;
import com.hotbox.jaitymangareader.config.security.Role;
import com.hotbox.jaitymangareader.core.error.ApiException;
import com.hotbox.jaitymangareader.core.error.ErrorCode;
import com.hotbox.jaitymangareader.user.service.AppUserService;
import com.hotbox.jaitymangareader.audit.service.AuditEventService;
import com.hotbox.jaitymangareader.auth.services.RefreshTokenService;
import com.hotbox.jaitymangareader.auth.services.RevokedTokenService;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final RevokedTokenService revokedTokenService;
    private final AuditEventService auditEventService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletRequest httpRequest) {

        AppUser user = userService.findByEmail(request.email())
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_INVALID_CREDENTIALS));

        if (!user.isEnabled()) {
            throw new ApiException(ErrorCode.AUTH_ACCOUNT_DISABLED);
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ApiException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        String accessToken = jwtUtils.generateToken(user);
        String ip = httpRequest.getRemoteAddr();
        String ua = httpRequest.getHeader("User-Agent");

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user, ip, ua);
        auditEventService.logLogin(user.getId(), ip, ua);

        return ResponseUtil.success(
                new TokenResponse(accessToken, refreshToken.getToken(), "Bearer", jwtUtils.getExpirationMs() / 1000),
                "Inicio de sesión exitoso",
                httpRequest
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request,
            HttpServletRequest httpRequest) {

        if (userService.findByEmail(request.email()).isPresent()) {
            throw new ApiException(ErrorCode.USER_EMAIL_CONFLICT);
        }

        AppUser user = AppUser.builder()
                .fullName(request.fullName())
                .photoUrl(request.photoUrl())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.CLIENT)
                .enabled(true)
                .createdAt(Instant.now())
                .build();

        AppUser saved = userService.save(user);

        String accessToken = jwtUtils.generateToken(saved);
        String ip = httpRequest.getRemoteAddr();
        String ua = httpRequest.getHeader("User-Agent");

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(saved, ip, ua);

        return ResponseUtil.created(
                new TokenResponse(accessToken, refreshToken.getToken(), "Bearer", jwtUtils.getExpirationMs() / 1000),
                "Registro exitoso. Sesión iniciada",
                httpRequest
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authHeader,
            HttpServletRequest request) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ApiException(ErrorCode.AUTH_TOKEN_INVALID);
        }

        String accessToken = authHeader.substring(7);
        Claims claims = jwtUtils.parseToken(accessToken);

        String jti = claims.getId();
        String userId = claims.getSubject();

        revokedTokenService.revokeToken(jti, userId);
        refreshTokenService.revokeTokensForUser(userId);

        auditEventService.logLogout(userId, jti, request.getRemoteAddr());

        return ResponseUtil.success(null, "Sesión cerrada correctamente", request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody @Valid RefreshRequest request, HttpServletRequest httpRequest) {

        RefreshToken refreshToken = refreshTokenService.verifyToken(request.refreshToken())
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_REFRESH_REVOKED));

        AppUser user = userService.getById(refreshToken.getUserId());
        String newAccessToken = jwtUtils.generateToken(user);

        return ResponseUtil.success(
                new TokenResponse(newAccessToken, refreshToken.getToken(), "Bearer", jwtUtils.getExpirationMs() / 1000),
                "Token renovado correctamente",
                httpRequest
        );
    }
}
