package com.placeholder.medisense.service;

import com.placeholder.medisense.model.User;
import com.placeholder.medisense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements ReactiveUserDetailsService {

  private final UserRepository userRepository;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .map(User::toUserDetails)
        .switchIfEmpty(
            Mono.error(new UsernameNotFoundException("Username: {} not found in records")));
  }
}
