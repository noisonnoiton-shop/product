package com.skcc.product.repository;

import java.util.List;

import com.skcc.product.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{
  public Product findById(long id);
  public List<Product> findByActive(String active);
  public List<Product> findProductByCategoryId(long categoryId);
  public List<Product> findProductBySalePercentageGreaterThan(long pct);
  
}
