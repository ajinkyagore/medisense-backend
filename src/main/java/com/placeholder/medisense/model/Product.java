package com.placeholder.medisense.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

  @Id private int id;
  private String name;
  private String description;
  private double mrp;
  private int quantityInStock;
  private String manufacturer;
  private String categoryId;
  private String imageId;
  private String resourceId;
}
