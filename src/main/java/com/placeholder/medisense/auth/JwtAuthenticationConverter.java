package com.placeholder.medisense.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

  private static final String BEARER_PREFIX = "Bearer ";

  private final JwtUtil jwUtil;

  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange.getRequest())
        .flatMap(this::resolveToken)
        .flatMap(jwUtil::validateToken)
        .flatMap(UserNamePasswordAuthBearer::create);
  }

  private Mono<String> resolveToken(ServerHttpRequest request) {
    String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    String token = null;
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      token = bearerToken.substring(BEARER_PREFIX.length());
    }
    return Mono.justOrEmpty(token);
  }
}
