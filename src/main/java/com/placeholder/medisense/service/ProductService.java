package com.placeholder.medisense.service;

import com.placeholder.medisense.model.Product;
import com.placeholder.medisense.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public Flux<Product> getAll() {
    return productRepository.findAll();
  }

  public Mono<Product> save(Product product) {
    return productRepository.save(product);
  }
}
