package com.hotbox.jaitymangareader.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.hotbox.jaitymangareader.auth.dto.FirebaseLoginRequest;
import com.hotbox.jaitymangareader.auth.dto.TokenResponse;
import com.hotbox.jaitymangareader.auth.services.RefreshTokenService;
import com.hotbox.jaitymangareader.config.security.JwtUtils;
import com.hotbox.jaitymangareader.config.security.Role;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import com.hotbox.jaitymangareader.user.entity.AppUser;
import com.hotbox.jaitymangareader.user.repository.AppUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class FirebaseAuthController {

    private final AppUserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/firebase-login")
    public ResponseEntity<?> firebaseLogin(
            @RequestBody FirebaseLoginRequest request,
            HttpServletRequest httpRequest) throws Exception {

        // ✅ Verificar ID token recibido
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(request.idToken());
        String uid = decodedToken.getUid();
        String email = decodedToken.getEmail();

        System.out.println(decodedToken.getClaims());
        // Obtener info detallada del usuario desde Firebase
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);

        String provider = "firebase";

        // ✅ Buscar o crear usuario localmente
        AppUser user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    AppUser newUser = AppUser.builder()
                            .email(email)
                            .role(Role.CLIENT)
                            .enabled(true)
                            .password("")
                            .createdAt(Instant.now())
                            .fullName(userRecord.getDisplayName())
                            .photoUrl(userRecord.getPhotoUrl())
                            .provider(provider)
                            .providerId(uid)
                            .build();
                    return userRepository.save(newUser);
                });

        // 🔐 Generar token
        String accessToken = jwtUtils.generateToken(user);
        String ip = httpRequest.getRemoteAddr();
        String ua = httpRequest.getHeader("User-Agent");

        var refreshToken = refreshTokenService.createRefreshToken(user, ip, ua);

        return ResponseUtil.success(
                new TokenResponse(accessToken, refreshToken.getToken(), "Bearer", jwtUtils.getExpirationMs() / 1000),
                "Inicio de sesión exitoso",
                httpRequest
        );


    }
}
