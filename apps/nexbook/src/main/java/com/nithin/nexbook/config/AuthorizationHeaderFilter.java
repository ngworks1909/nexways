package com.nithin.nexbook.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthorizationHeaderFilter extends OncePerRequestFilter {

    private final TokenParser tokenParser;

    public AuthorizationHeaderFilter(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

        if (token == null || token.isEmpty() || tokenParser.getAllClaims(token).get("userId", String.class).equals(null)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Authorization header missing or invalid authentication token\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
