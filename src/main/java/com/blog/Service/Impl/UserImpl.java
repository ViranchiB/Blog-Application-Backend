package com.blog.Service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Exception.ResourceNotFoundException;
import com.blog.Payload.UserDto;
import com.blog.Service.UserService;
import com.blog.entities.User;
import com.blog.repository.UserRepo;

@Service
public class UserImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		/* convert the DTO user to normal user 
			because normal user can only connect with the SQL 
			because it has annotations given @ID etc which work with SQL
			thats why we have to convert the DtoUser to User
		*/
		User user = this.dtoToUser(userDto);
		
		// Saving the user which has @Entity annotation
		User savedUser = userRepo.save(user);
		
		// Returning the DTO user to client (to hide the entity from real world)
		return this.userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		// Find user by id
		User userById = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with ID : ", userId));
		
		// update user data 
		userById.setName(userDto.getName());
		userById.setEmail(userDto.getEmail());
		userById.setPassword(userDto.getPassword());
		userById.setAbout(userDto.getAbout());
		
		// save newly updated user and return DTO user.
		User updatesUser = userRepo.save(userById);
		
		// Convert the User to DTO
		UserDto userToUserDto = this.userToUserDto(updatesUser);
		
		return userToUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		// Find user by id
		User userById = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with ID : ", userId));
				
		// convert the user to DTO user and return.		
		return this.userToUserDto(userById);
	}

	@Override
	public List<UserDto> getAllUsers() {
		 
		// Fetch the list of user
		List<User> listOfUsers = userRepo.findAll();
		
		// Convert the list of user and store it in the userDTO
		List<UserDto> userDtos = listOfUsers.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		
		//return userDTO
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		// Find user by id
		User userById = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with ID: ", userId));
		userRepo.delete(userById);
	}
	
// ==================================Convert the Objects============================================
	
	public User dtoToUser(UserDto userDto) {
		
		// 1. Make the maodelMapper object by @Autowired
		// Convert the object
		User user = this.modelMapper.map(userDto, User.class);
		
		// 2. return the normal user which work with SQL
		return user;
		
	}

	public UserDto userToUserDto(User user) {
		
		// 1. COnver the object
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		return userDto;
	}
	

}
