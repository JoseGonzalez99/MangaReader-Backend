package com.hotbox.jaitymangareader.config.security;


import com.hotbox.jaitymangareader.user.entity.AppUser;
import com.hotbox.jaitymangareader.user.repository.AppUserRepository;
import com.hotbox.jaitymangareader.audit.service.AuditEventService;
import com.hotbox.jaitymangareader.auth.services.RevokedTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AppUserRepository userRepository;
    private final RevokedTokenService revokedTokenService;
    private final AuditEventService auditEventService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println("🛡️ Authorization Header: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtils.parseToken(token);
            String userId = claims.getSubject();
            String role = claims.get("role", String.class);
            String jti = claims.getId(); // extraer el jti
            auditEventService.logAccess(
                    userId,
                    claims.getId(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    request.getRequestURI()
            );

            // ⛔ Verificar si el jti fue revocado
            if (revokedTokenService.isTokenRevoked(jti)) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            // Para auditar access tokens
            auditEventService.logAccess(
                    userId,
                    claims.getId(),                      // jti
                    request.getRemoteAddr(),            // IP
                    request.getHeader("User-Agent"),    // User-Agent
                    request.getRequestURI()             // Ruta accedida
            );

            AppUser appUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            UserDetails userDetails = User.withUsername(appUser.getEmail())
                    .password(appUser.getPassword())
                    .roles(role)
                    .build();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            // Token inválido o expirado
            SecurityContextHolder.clearContext(); // Opcional
        }

        chain.doFilter(request, response);
    }
}
