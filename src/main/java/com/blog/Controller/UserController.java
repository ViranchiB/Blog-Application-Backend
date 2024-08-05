package com.blog.Controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.blog.Payload.UserDto;
import com.blog.Service.UserService;
import com.blog.entities.User;
import com.blog.repository.UserRepo;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users/")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		// create a new user
		UserDto createdUser = userService.createUser(userDto);
		
		// return the newly created user and the response of the API
		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
		
		return new ResponseEntity<UserDto>(userService.getUserById(userId), HttpStatus.FOUND);
//		return ResponseEntity.ok(userService.getUserById(userId));
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> listOfUsers() {
		return new ResponseEntity<List<UserDto>>(userService.getAllUsers(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<APIResponse> deleteUser(@PathVariable Integer userId){
		userService.deleteUser(userId);
		return new ResponseEntity<APIResponse>(new APIResponse("User deleted suucessfully",true), HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
		UserDto updateUser = userService.updateUser(userDto, userId);
		
		return ResponseEntity.ok(updateUser);
	}
	
}
