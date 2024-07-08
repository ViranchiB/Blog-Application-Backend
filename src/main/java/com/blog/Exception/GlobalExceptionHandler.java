package com.blog.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.Payload.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> resourceNotFOundExceptionHandler(ResourceNotFoundException ex){
		
		String msg = ex.getMessage();
		
		return new ResponseEntity<APIResponse>(new APIResponse(msg, false), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
		
		// create a empty map to store the field name and error msg
		Map<String,String> resp = new HashMap<>();
		
		// getting all the error and field names
		/*
		 	1. ex.getBindingResult() = The ex.getBindingResult() method in the MethodArgumentNotValidException class retrieves a BindingResult object.
		 	2. FieldError = It is the object which holds the field name
		*/
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			
			// adding it to map
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler(CategoryNotFoundException.class)
//	public ResponseEntity<APIResponse> CategoryNotFoundExceptionHandler(CategoryNotFoundException ex){
//		
//		String msg = ex.getMessage();
//											/* we have to create a new object because in API response 
//											   we use constracture thays why we have to pass the argument in
//											   constructore*/
//		return new ResponseEntity<APIResponse>(new APIResponse(msg, false),HttpStatus.BAD_REQUEST);
//	}
}
