package com.placeholder.medisense.repository;

import com.placeholder.medisense.model.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, String> {}
