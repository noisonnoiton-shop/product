package com.skcc.category.service;

import java.util.List;

import com.skcc.category.domain.Category;
import com.skcc.category.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	// @Autowired
	// private CategoryMapper categoryMapper;

	private CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<Category> findAll() {
		// return this.categoryMapper.findAll();
		return this.categoryRepository.findAll();
	}
}
