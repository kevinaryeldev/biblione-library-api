package com.biblione.library_api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
public class TokenService {

    private final SecretKey secretKey;

    public TokenService(@Value("${biblione.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims validateAndExtractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException ex) {
            log.warn("Invalid JWT token: {}", ex.getMessage());
            throw new com.biblione.library_api.exception.BusinessException(
                    "Token inválido ou expirado.",
                    org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(validateAndExtractClaims(token).getSubject());
    }

    public String extractRole(String token) {
        return validateAndExtractClaims(token).get("role", String.class);
    }
}