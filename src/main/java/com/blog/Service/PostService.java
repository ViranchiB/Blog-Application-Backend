package com.blog.Service;

import java.util.List;

import com.blog.Payload.PostDto;

public interface PostService{

	//create
	// TO get the user and category we are passing the id
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//get post by id
	PostDto getPostById(Integer postId);
	
	// update post by id
	PostDto updatePostById(PostDto postDto, Integer postId);
	
	// delete post by id
	void deletePost(Integer postId);
	
	// list of all post
	List<PostDto> getAllPost();
	
	//get all post by user
	List<PostDto> getPostByUser(Integer userId);
	
	//get all post by category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//search post
	List<PostDto> searchPostByTitle(String title);
}
