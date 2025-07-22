package com.nithin.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${gateway.secret}")
    private String GATEWAY_SECRET;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_route", r -> r.path("/api/auth/v1/**")
                        .filters(f -> f.addRequestHeader("X-GATEWAY-SECRET", GATEWAY_SECRET))
                        .uri("http://localhost:3001"))
                .build();

    }
}

