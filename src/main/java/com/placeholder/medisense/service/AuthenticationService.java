package com.placeholder.medisense.service;

import com.placeholder.medisense.dto.UserSignUpDTO;
import com.placeholder.medisense.enums.UserRole;
import com.placeholder.medisense.model.User;
import com.placeholder.medisense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public Mono<User> signUp(@RequestBody UserSignUpDTO userSignUpDTO) {
    return userRepository.save(toUser(userSignUpDTO, UserRole.USER));
  }

  private User toUser(UserSignUpDTO userSignUpDTO, UserRole role) {
    String encodedPasword = passwordEncoder.encode(userSignUpDTO.getPassword());
    return User.builder()
        .username(userSignUpDTO.getUsername())
        .password(encodedPasword)
        .email(userSignUpDTO.getEmail())
        .name(userSignUpDTO.getName())
        .role(role)
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .enabled(true)
        .build();
  }
}
