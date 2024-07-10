package com.blog.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Payload.APIResponse;
import com.blog.Payload.CategoryDto;
import com.blog.Service.CategoryService;
import com.blog.entities.Category;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		return new ResponseEntity<CategoryDto>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
		return new ResponseEntity<CategoryDto>(categoryService.getCategoryById(categoryId), HttpStatus.FOUND);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> listOfCatogery(){
		return new ResponseEntity<List<CategoryDto>>(categoryService.listOfCategory(), HttpStatus.FOUND);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		
		CategoryDto categoryById = categoryService.updateCategory(categoryDto, categoryId);
		
		return new ResponseEntity<CategoryDto>(categoryById, HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<APIResponse> deletUserById(@PathVariable Integer categoryId){
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<APIResponse>(new APIResponse("User deleted sucesfully", true), HttpStatus.OK);
	}
}
