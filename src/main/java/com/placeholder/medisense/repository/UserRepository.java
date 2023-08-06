package com.placeholder.medisense.repository;

import com.placeholder.medisense.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

  // TODO: Enable search by username or email
  public Mono<User> findByUsername(String username);
}
