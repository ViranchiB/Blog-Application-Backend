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
import com.blog.Service.CategoryService;
import com.blog.entities.Category;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category){
		return new ResponseEntity<Category>(categoryService.createCategory(category), HttpStatus.CREATED);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Integer categoryId){
		return new ResponseEntity<Category>(categoryService.getCategoryById(categoryId), HttpStatus.FOUND);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Category>> listOfCatogery(){
		return new ResponseEntity<List<Category>>(categoryService.listOfCategory(), HttpStatus.FOUND);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category, @PathVariable Integer categoryId){
		
		Category categoryById = categoryService.updateCategory(category, categoryId);
		
		return new ResponseEntity<Category>(categoryById, HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<APIResponse> deletUserById(@PathVariable Integer categoryId){
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<APIResponse>(new APIResponse("User deleted sucesfully", true), HttpStatus.OK);
	}
}
