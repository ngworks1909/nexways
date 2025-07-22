package com.nithin.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String getJwtSecret() {
        return secret;
    }

    public long getJwtExpiration() {
        return expiration;
    }
}
