package com.nithin.nexbook.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;


@Service
public class TokenParser {
    private final SecretConfig jwtConfig;

    public TokenParser(SecretConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getJwtSecret().getBytes(StandardCharsets.UTF_8));
    }

    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
