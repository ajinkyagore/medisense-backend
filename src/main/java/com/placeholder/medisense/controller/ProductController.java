package com.placeholder.medisense.controller;

import com.placeholder.medisense.model.Product;
import com.placeholder.medisense.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

  private final ProductService productService;

  @GetMapping("/product")
  public Flux<Product> getProducts() {
    return productService.getAll();
  }

  @PostMapping("/product")
  public Mono<Product> createProduct(@RequestBody Product product) {
    return productService.save(product);
  }

  @GetMapping("/")
  public Mono<String> welcome() {
    return Mono.just("Welcome to Medisense!");
  }
}
