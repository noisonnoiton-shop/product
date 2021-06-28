package com.skcc.category.repository;

import com.skcc.category.domain.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
  public Category findById(long id);
}
