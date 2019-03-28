package com.mazzama.productservice;

import com.mazzama.productservice.entity.Product;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by azzam on 28/03/19.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

  private static Logger logger = LoggerFactory.getLogger(ProductController.class);

  @GetMapping("/{userId}")
  public ProductList getProduct(@PathVariable String userId) {

    logger.info("userId: ", userId);

    ProductList productList = new ProductList();
    List<Product> products = new ArrayList<>();

    Product product1 = new Product(1L, "Adidas Shoes", 120.0);
    products.add(product1);

    Product product2 = new Product(2L, "New Balance Shoes", 110.0);
    products.add(product2);

    productList.setProducts(products);
    return productList;
  }

}
