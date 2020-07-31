package com.skcc.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.product.domain.Product;
import com.skcc.product.event.message.ProductEvent;
import com.skcc.product.service.ProductService;

@RestController
@RequestMapping("/v1")
public class ProductController {
	
	private ProductService productService;
	
	@Autowired
	public ProductController (ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(value="/products/category/{categoryId}")
	public List<Product> findByCategoryId(@PathVariable long categoryId){
		return this.productService.findByCategoryId(categoryId);
	}
	
	@GetMapping(value="/products")
	public List<Product> getAllProducts(){
		return this.productService.getAllProducts();
	}
	
	@GetMapping(value="/products/sale")
	public List<Product> findByCategoryId(){
		return this.productService.findProductOnSale();
	}
	
	@GetMapping(value="/products/{id}")
	public Product findById(@PathVariable(value="id") long id) {
		return this.productService.findById(id);
	}
	
	@GetMapping(value="/products/events")
	public List<ProductEvent> getProductEvent(){
		return this.productService.getProductEvent();
	}
}
