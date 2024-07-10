package com.blog.Service;

import java.util.List;

import com.blog.Payload.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto getCategoryById(int categoryId);
	
	CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);
	
	List<CategoryDto> listOfCategory();
	
	void deleteCategory(int categoryId);
}
