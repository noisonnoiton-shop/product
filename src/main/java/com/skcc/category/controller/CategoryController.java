package com.skcc.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.category.domain.Category;
import com.skcc.category.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
public class CategoryController {
	
	private CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping(value="/categories")
	public List<Category> getCategories(){
		return categoryService.findAll();
	}
}
