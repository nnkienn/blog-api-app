package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.CategoryDto;

public interface CategoryServices {
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	public void deleteCategory(Integer categoryId);
	CategoryDto getCategory(Integer categoryId);
	
	List<CategoryDto> getCategory();

}
