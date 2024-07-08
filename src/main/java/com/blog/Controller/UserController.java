package com.blog.Controller;

import java.util.List;

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
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		
		// create a new user
		User createdUser = userService.createUser(user);
		
		// return the newly created user and the response of the API
		return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Integer userId){
		
		return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.FOUND);
//		return ResponseEntity.ok(userService.getUserById(userId));
	}
	
	@GetMapping("/")
	public ResponseEntity<List<User>> listOfUsers() {
		return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<APIResponse> deleteUser(@PathVariable Integer userId){
		userService.deleteUser(userId);
		return new ResponseEntity<APIResponse>(new APIResponse("User deleted suucessfully",true), HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user, @PathVariable Integer userId){
		User updateUser = userService.updateUser(user, userId);
		
		return ResponseEntity.ok(updateUser);
	}
	
}
