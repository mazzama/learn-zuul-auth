package com.mazzama.productservice.entity;

import java.io.Serializable;

/**
 * Created by azzam on 28/03/19.
 */
public class Product implements Serializable {

  private Long id;
  private String name;
  private Double basePrice;

  public Product(final Long id, final String name, final Double basePrice) {
    this.id = id;
    this.name = name;
    this.basePrice = basePrice;
  }

  public Product() {}

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Double getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(final Double basePrice) {
    this.basePrice = basePrice;
  }
}
