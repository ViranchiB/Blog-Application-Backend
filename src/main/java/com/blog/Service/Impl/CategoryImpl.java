package com.blog.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Exception.ResourceNotFoundException;
import com.blog.Service.CategoryService;
import com.blog.entities.Category;
import com.blog.repository.CategoryRepository;

@Service
public class CategoryImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category getCategoryById(int categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Catogey not found along with ID ",categoryId));
	}

	@Override
	public Category updateCategory(Category category, int CategoryId) {
		
		Category findCategroyById = categoryRepository.findById(CategoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found along with the ID ", CategoryId));
		
		findCategroyById.setCategoryTitle(category.getCategoryTitle());
		findCategroyById.setCategoryDescription(category.getCategoryDescription());
		
		return categoryRepository.save(findCategroyById);
	}

	@Override
	public List<Category> listOfCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public void deleteCategory(int categoryId) {
		
		Category findCategoryById = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Catogey not found along with ID ", categoryId));
		
		categoryRepository.delete(findCategoryById);
	}

}
