package com.mazzama.productservice;

import com.mazzama.productservice.entity.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by azzam on 28/03/19.
 */
public class ProductList implements Serializable {

  private List<Product> products;

  public ProductList(final List<Product> products) {
    this.products = products;
  }

  public ProductList() {
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(final List<Product> products) {
    this.products = products;
  }
}
