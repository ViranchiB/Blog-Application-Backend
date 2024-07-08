package com.blog.Service;

import java.util.List;

import com.blog.entities.User;

public interface UserService {

	User createUser(User user);
	
	User updateUser(User user, Integer userId);
	
	User getUserById(Integer userId);
	
	List<User> getAllUsers();
	
	void deleteUser(Integer userId);
}
