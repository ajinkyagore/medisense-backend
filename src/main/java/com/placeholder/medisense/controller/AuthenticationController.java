package com.placeholder.medisense.controller;

import com.placeholder.medisense.dto.UserSignUpDTO;
import com.placeholder.medisense.model.User;
import com.placeholder.medisense.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @GetMapping("/login")
  public Mono<String> login() {
    return Mono.just("authenticated");
    // return authenticationService.login(loginDTO);
  }

  @PostMapping("/signup")
  public Mono<User> signUp(@RequestBody UserSignUpDTO signUpDTO) {
    return authenticationService.signUp(signUpDTO);
  }
}
