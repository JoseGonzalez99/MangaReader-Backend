package com.hotbox.jaitymangareader.security;

import com.hotbox.jaitymangareader.entity.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String secret;
    @Getter
    @Value("${app.jwt.expiration}")
    private long expirationMs;


    private static final String ISSUER = "jaity-api";
    private static final String AUDIENCE = "jaity-client";


    public String generateToken(AppUser user) {
        String jti = UUID.randomUUID().toString();
        return Jwts.builder()
                .subject(user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .issuer(ISSUER)
                .audience().add(AUDIENCE).and()
                .id(jti)
                .signWith(getSigningKey())
                .compact();
    }

    public Claims parseToken(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(getSigningKey()).build();

        Claims claims = parser.parseSignedClaims(token).getPayload();

        // Validaciones extra
        if (!ISSUER.equals(claims.getIssuer()) || !claims.getAudience().contains(AUDIENCE)) {
            throw new RuntimeException("Token inválido (issuer/audience)");
        }

        return claims;
    }


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

}
