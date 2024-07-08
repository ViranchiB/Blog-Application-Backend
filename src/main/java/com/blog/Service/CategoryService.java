package com.blog.Service;

import java.util.List;

import com.blog.entities.Category;

public interface CategoryService {

	Category createCategory(Category category);
	
	Category getCategoryById(int categoryId);
	
	Category updateCategory(Category category, int categoryId);
	
	List<Category> listOfCategory();
	
	void deleteCategory(int categoryId);
}
