package com.placeholder.medisense.config;

import com.placeholder.medisense.auth.BasicAuthenticationSuccessHandler;
import com.placeholder.medisense.auth.JwtAuthenticationConverter;
import com.placeholder.medisense.auth.JwtAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ReactiveUserDetailsService userDetailsService;
  private final BasicAuthenticationSuccessHandler basicAuthenticationSuccessHandler;
  private final JwtAuthenticationConverter jwtConverter;
  private final JwtAuthenticationManager jwtAuthenticationManager;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
    http
        // .httpBasic(Customizer.withDefaults())
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        // .csrf(Customizer.withDefaults())
        .authorizeExchange(
            exchanges ->
                exchanges.pathMatchers("/signup").permitAll().anyExchange().authenticated())
        .addFilterBefore(getJwtAuthFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
        .addFilterAt(getBasicAuthFilter(), SecurityWebFiltersOrder.HTTP_BASIC);
    return http.build();
  }

  public ReactiveAuthenticationManager reactiveAuthenticationManager() {
    UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
        new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    authenticationManager.setPasswordEncoder(passwordEncoder());
    return authenticationManager;
  }

  private AuthenticationWebFilter getBasicAuthFilter() {
    AuthenticationWebFilter authenticationWebFilter =
        new AuthenticationWebFilter(reactiveAuthenticationManager());
    authenticationWebFilter.setAuthenticationSuccessHandler(basicAuthenticationSuccessHandler);
    return authenticationWebFilter;
  }

  private AuthenticationWebFilter getJwtAuthFilter() {
    AuthenticationWebFilter authenticationWebFilter =
        new AuthenticationWebFilter(jwtAuthenticationManager);
    authenticationWebFilter.setServerAuthenticationConverter(jwtConverter);
    return authenticationWebFilter;
  }
}
