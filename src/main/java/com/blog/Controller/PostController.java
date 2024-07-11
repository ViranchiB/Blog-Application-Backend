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
import com.blog.Payload.PostDto;
import com.blog.Service.PostService;
import com.blog.entities.Post;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	// 1. Create
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){

		return new ResponseEntity<PostDto>(postService.createPost(postDto, userId, categoryId), HttpStatus.CREATED);
	}
	
	// 2. Get Post By User
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		
		List<PostDto> postByUser = postService.getPostByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(postByUser,HttpStatus.OK);
	}
	
	// 3. Get Post By Category
	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		
		List<PostDto> postByCategory = postService.getPostByCategory(categoryId);
			
		return new ResponseEntity<List<PostDto>>(postByCategory, HttpStatus.OK);
	}
	
	// 4. List of Post
	@GetMapping("/post")
	public ResponseEntity<List<PostDto>> listOfPost(){
		
		// fetch all the post
		List<PostDto> listOfPost = postService.getAllPost();
		
		//return the list
		return new ResponseEntity<List<PostDto>>(listOfPost, HttpStatus.OK);
	}
	
	// 5. Get Post By Post ID
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		
		//fetch the post by id
		PostDto postById = postService.getPostById(postId);
		
		//return the post
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<APIResponse> deletePostById(@PathVariable Integer postId){
		
		postService.deletePost(postId);
		return new ResponseEntity<APIResponse>(new APIResponse("Post deleted sucessfully", true), HttpStatus.OK);
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		
		// fetch
		PostDto post = postService.updatePostById(postDto, postId);
		
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
		
		// fetch
		List<PostDto> result = postService.searchPostByTitle(keyword);
		
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}
	
}
