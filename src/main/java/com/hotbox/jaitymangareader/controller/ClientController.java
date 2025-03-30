package com.hotbox.jaitymangareader.controller;

import com.hotbox.jaitymangareader.dto.ChangePasswordRequest;
import com.hotbox.jaitymangareader.dto.UpdateProfileRequest;
import com.hotbox.jaitymangareader.entity.AppUser;

import com.hotbox.jaitymangareader.error.ApiException;
import com.hotbox.jaitymangareader.error.ErrorCode;
import com.hotbox.jaitymangareader.service.AppUserService;
import com.hotbox.jaitymangareader.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENT') or hasRole('STAFF')")
public class ClientController {

    private final AppUserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> getProfile(HttpServletRequest req, Authentication auth) {
        AppUser user = userService.findByEmail(auth.getName())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        return ResponseUtil.success(user, "Perfil cargado correctamente", req);
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(
            Authentication auth,
            @RequestBody @Valid UpdateProfileRequest request,
            HttpServletRequest req) {

        AppUser user = getUserFromAuth(auth);
        user.setEmail(request.email());

        AppUser updated = userService.save(user);
        return ResponseUtil.success(updated, "Perfil actualizado correctamente", req);
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(
            Authentication auth,
            @RequestBody @Valid ChangePasswordRequest request,
            HttpServletRequest req) {

        AppUser user = getUserFromAuth(auth);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new ApiException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userService.save(user);

        return ResponseUtil.noContent("Contraseña actualizada correctamente.", req);
    }

    private AppUser getUserFromAuth(Authentication auth) {
        return userService.findByEmail(auth.getName())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }
}
