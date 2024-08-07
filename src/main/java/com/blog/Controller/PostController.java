package com.blog.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.Payload.APIResponse;
import com.blog.Payload.PostDto;
import com.blog.Service.FileService;
import com.blog.Service.PostService;
import com.blog.entities.Post;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	// 1. Create
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){

		return new ResponseEntity<PostDto>(postService.createPost(postDto, userId, categoryId), HttpStatus.CREATED);
	}
	
	// POST IMAGE UPLOAD
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		// Find post by ID to add particular image as per post
		PostDto postDto = this.postService.getPostById(postId);
		
		// Get file name
		String fileName = this.fileService.uplodImage(path, image);
		
		// Set updated filename
		postDto.setImageName(fileName);
		
		// Update post
		this.postService.updatePostById(postDto, postId);	
		
		// Return post
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
		
	}
	
	// get image (Serve image)
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(@PathVariable String imageName,
			HttpServletResponse servletResponse) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		
		servletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource, servletResponse.getOutputStream());
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
