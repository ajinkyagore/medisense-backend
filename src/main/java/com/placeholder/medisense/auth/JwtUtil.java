package com.placeholder.medisense.auth;

import com.placeholder.medisense.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtUtil {

  private static final String ISSUER = "Medisense";
  private static final long EXPIRE_DURATION_HOURS = 24;
  private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
  private final SecretKey key;

  public JwtUtil() {
    key = Keys.secretKeyFor(ALGORITHM);
  }

  public String createToken(User user) {
    return Jwts.builder()
        .setIssuer(ISSUER)
        .setSubject(user.getUsername())
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(EXPIRE_DURATION_HOURS, ChronoUnit.HOURS)))
        .signWith(key)
        .compact();
  }

  public Mono<Claims> validateToken(String token) {
    // TODO: Add custom excepiton
    return Mono.justOrEmpty(token)
        .flatMap(this::getClaims); // .switchIfEmpty(Mono.error(new RuntimeException("Invalid jwt
    // token")));
  }

  public Mono<Claims> getClaims(String token) {
    JwtParser parser = Jwts.parserBuilder().setSigningKey(key).requireIssuer(ISSUER).build();
    return Mono.just(token)
        .map(parser::parseClaimsJws)
        .map(Jwt::getBody)
        .onErrorResume(e -> Mono.empty());
  }
}
