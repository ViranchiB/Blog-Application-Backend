package com.blog.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Exception.ResourceNotFoundException;
import com.blog.Service.UserService;
import com.blog.entities.User;
import com.blog.repository.UserRepo;

@Service
public class UserImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public User createUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User updateUser(User user, Integer userId) {
		
		// Find user by id
		User userById = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with ID : ", userId));
		
		// update user data 
		userById.setName(user.getName());
		userById.setEmail(user.getEmail());
		userById.setPassword(user.getPassword());
		userById.setAbout(user.getAbout());
		
		// save newly updated user and return it.
		User updatesUser = userRepo.save(userById);
		return updatesUser;
	}

	@Override
	public User getUserById(Integer userId) {
		
		// Find user by id
		User userById = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with ID : ", userId));
				
		return userById;
	}

	@Override
	public List<User> getAllUsers() {
		 
		return userRepo.findAll();
	}

	@Override
	public void deleteUser(Integer userId) {
		
		// Find user by id
		User userById = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with ID: ", userId));
		userRepo.delete(userById);
	}

	

}
