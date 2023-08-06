package com.placeholder.medisense.auth;

import com.placeholder.medisense.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BasicAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

  private final JwtUtil jwUtil;

  @Override
  public Mono<Void> onAuthenticationSuccess(
      WebFilterExchange webFilterExchange, Authentication authentication) {
    webFilterExchange
        .getExchange()
        .getResponse()
        .getHeaders()
        .add("AUTHORIZATION", "Bearer " + jwUtil.createToken((User) authentication.getPrincipal()));
    return webFilterExchange.getChain().filter(webFilterExchange.getExchange());
  }
}
