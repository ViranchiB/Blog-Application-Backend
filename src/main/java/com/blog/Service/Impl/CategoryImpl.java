package com.blog.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Exception.ResourceNotFoundException;
import com.blog.Payload.CategoryDto;
import com.blog.Service.CategoryService;
import com.blog.entities.Category;
import com.blog.repository.CategoryRepository;

@Service
public class CategoryImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	// 1. CREATE
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		// 1. For "Create" firstly convert the DTO Category to Normal Category class
		Category category = this.dtoToCategory(categoryDto);
		
		//2. Save the category because only normal entity can only communicate with the SQL DB.
		Category saveCategory = categoryRepository.save(category);
		
		//3. Convert the normal category to DTO Category because we want to return the response body of saved object
		CategoryDto categoryToDto = this.categoryToDto(saveCategory);
		
		// return the DTO object. Need to send the saved response back to client
		return categoryToDto;
	}

	// 2. Get By ID
	@Override
	public CategoryDto getCategoryById(int categoryId) {
		
		// 1. Fetch category by Id form SQL for which we need real Entity class. 
		Category categoryById = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Catogey not found along with ID ",categoryId));
		
		// 2. After fetching convert it to DTO to send the response to client.
		CategoryDto categoryDto = this.categoryToDto(categoryById);
		
		// 3. Return the converted DTO object
		return categoryDto;
		
	}

	// 3. UPDATE
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int CategoryId) {
		
		// 1. Fetch the record by ID from SQL which will be done by orignal entity
		Category CategroyById = categoryRepository.findById(CategoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found along with the ID ", CategoryId));
		
		// 2. Set the updated value 
		CategroyById.setCategoryTitle(categoryDto.getCategoryTitle());
		CategroyById.setCategoryDescription(categoryDto.getCategoryDescription());
		
		// 3. Save the entity category object to SQL.
		Category updateCategory = categoryRepository.save(CategroyById);
		
		// 4. Convert Entity category to DTO Category
		CategoryDto categoryToDto = this.categoryToDto(updateCategory);
		
		// 5. Return DTO object
		return categoryToDto;
	}

	// 4. List of Category
	@Override
	public List<CategoryDto> listOfCategory() {

		// 1. Fetch all the category from SQL
		List<Category> listOfCategory = categoryRepository.findAll();
		
		// 2. Convert the entity list of category to list of DTO Category
		List<CategoryDto> listOfDtoCategory = listOfCategory.stream().map(category->this.categoryToDto(category)).collect(Collectors.toList());
		
		// 3. Return the list of DTO Category
		return listOfDtoCategory;
	}

	// 5. Delete Category
	@Override
	public void deleteCategory(int categoryId) {
		
		Category findCategoryById = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Catogey not found along with ID ", categoryId));
		
		categoryRepository.delete(findCategoryById);
	}
	
	
//=============================================Convert the Objects==========================================
	
	public CategoryDto categoryToDto(Category category) {
		
		// On which class you want to convert the your object
		
		//                                   Object Source, Object Destination Class   
		CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
		
		return categoryDto;
	}
	
	public Category dtoToCategory(CategoryDto categoryDto) {
		
		Category category = modelMapper.map(categoryDto, Category.class);
		
		return category;
	}

}
