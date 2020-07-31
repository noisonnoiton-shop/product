package com.skcc.category.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.skcc.category.domain.Category;

@Mapper
public interface CategoryMapper {
	
//	@Select("select * from categories where active = 'active' order by priority")
	public List<Category> findAll();
}
