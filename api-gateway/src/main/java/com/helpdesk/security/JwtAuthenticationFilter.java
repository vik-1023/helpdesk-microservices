package com.helpdesk.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class JwtAuthenticationFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                            GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        System.out.println("METHOD = " + method);

        if (path.startsWith("/auth/")) {
            return chain.filter(exchange);
        }

        String header =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token = header.substring(7);

        try {

            Claims claims =
                    JwtUtil.validateToken(token);

            String role =
                    claims.get("role", String.class);

            String username =
                    claims.getSubject();

            System.out.println("PATH = " + path);
            System.out.println("ROLE = " + role);
            System.out.println("USERNAME = " + username);

            // GET ALL tickets → ADMIN only
            if (path.equals("/ticket-service/api/tickets")
                    && method.equals("GET")
                    && !role.equals("ADMIN")) {

                return forbidden(exchange);
            }

            // DELETE ticket → ADMIN only
            if (path.contains("/deleteTicketById")
                    && method.equals("DELETE")
                    && !role.equals("ADMIN")) {

                return forbidden(exchange);
            }

            // STATUS filter → ADMIN + AGENT
            if (path.contains("/getTicketByStatus")
                    && !(role.equals("ADMIN")
                    || role.equals("AGENT"))) {

                return forbidden(exchange);
            }

            // PRIORITY filter → ADMIN + AGENT
            if (path.contains("/getTicketByPriority")
                    && !(role.equals("ADMIN")
                    || role.equals("AGENT"))) {

                return forbidden(exchange);
            }

            exchange =
                    exchange.mutate()
                            .request(
                                    exchange.getRequest()
                                            .mutate()
                                            .header(
                                                    "X-User-Name",
                                                    username)
                                            .build())
                            .build();

        }

        catch (Exception e) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private Mono<Void> forbidden(
            ServerWebExchange exchange) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.FORBIDDEN);

        return exchange.getResponse().setComplete();
    }
}