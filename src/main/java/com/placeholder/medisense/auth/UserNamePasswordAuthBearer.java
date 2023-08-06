package com.placeholder.medisense.auth;

import io.jsonwebtoken.Claims;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class UserNamePasswordAuthBearer {

  public static Mono<Authentication> create(Claims claims) {
    return Mono.just(
        new UsernamePasswordAuthenticationToken(
            claims.getSubject(), null, Collections.emptyList()));
  }
}
