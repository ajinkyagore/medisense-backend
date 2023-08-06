package com.placeholder.medisense.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

  private final ReactiveUserDetailsService userDetailsService;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    // TODO: Create custom exception
    return Mono.just(authentication)
        .filter(Authentication::isAuthenticated)
        .map(Authentication::getPrincipal)
        .map(String.class::cast)
        .flatMap(userDetailsService::findByUsername)
        .map(UserDetails::isEnabled)
        .map(user -> authentication)
        .switchIfEmpty(Mono.error(new RuntimeException("User is not authenticated")));
  }
}
