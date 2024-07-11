package com.blog.Service.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Exception.ResourceNotFoundException;
import com.blog.Payload.PostDto;
import com.blog.Service.PostService;
import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.repository.CategoryRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepo;

@Service
public class PostImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		// 1. Find user by id
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found along with the ID ", userId));
		
		
		// 2. Finding category by ID
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found along with the ID ", userId));
		
		// 3. Convert the DTO to Entity object to store in SQL
		Post post = this.postDtoToPost(postDto);
		
		// set the user and category to the post object
		post.setUser(user);
		post.setCategory(category);
		
		// set the default image and date
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		
		// 4. Store it to SQL
		Post savePost = this.postRepository.save(post);
		
		// 5. Return entity to DTO
		return this.postTOpostDto(savePost);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		// 1. Fetch Post by ID from SQL (Entity)
		Post postById = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found along with the ID ", postId));
		
		// 2. Convert Entity to DTO and return		
		return this.postTOpostDto(postById);
	}

	@Override
	public void deletePost(Integer postId) {
		
		// 1. Fetch the data from SQL
		Post post = postRepository.findById(postId).orElseThrow( () -> (new ResourceNotFoundException("Post not found along with the ID ", postId)));
		
		// 2. Delete the fetch record
		postRepository.delete(post);
	}

	@Override
	public List<PostDto> getAllPost() {
		
		// 1. Fetch all the post
		List<Post> listOfPost = postRepository.findAll();
		
		// 2. Convert Post to postDto 
		List<PostDto> listOfpostDto = listOfPost.stream().map(post -> this.postTOpostDto(post)).collect(Collectors.toList());
		
		// 3. return
		return listOfpostDto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		// 1. Find user by user id
		User UserById = userRepo.findById(userId).orElseThrow(()->(new ResourceNotFoundException("User not found along with the ID ", userId)));
		
		// 2. fetch list of post based on users ID
		List<Post> post = postRepository.findByUser(UserById);
		
		// 3. Convert the object to list					
		// listOfPost = it is a local variable
		List<PostDto> lostOfPostByUserId = post.stream().map(listOfPost -> postTOpostDto(listOfPost)).collect(Collectors.toList());
		
		return lostOfPostByUserId;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		// 1. Find category by category id
		Category categoryById = categoryRepository.findById(categoryId).orElseThrow(()->(new ResourceNotFoundException("Category not found along with the ID ", categoryId)));
		
		// 2. Fetch the post based on category ID
		List<Post> post = postRepository.findByCategory(categoryById);
		
		// 3. Convert the object to list					
		// listOfPost = it is a local variable
		List<PostDto> lostOfPostByCategoryId = post.stream().map(listOfPost -> postTOpostDto(listOfPost)).collect(Collectors.toList());
				
		return lostOfPostByCategoryId;
	}
	
	@Override
	public PostDto updatePostById(PostDto postDto, Integer postId) {
		
		// 1. Fetch the Post from SQL
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found along with the ID ", postId));
		
		// 2. Update the required fields
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		// 3. Save the updated 
		Post savePost = postRepository.save(post);
		
		// We can directly use model mapper insted of using the model mapper custom created method to convert object
//		return postTOpostDto(savePost);
		return this.modelMapper.map(savePost, PostDto.class);  // with help of this we dont need to write 2 methods for map the objects.
		
	}

	@Override
	public List<PostDto> searchPostByTitle(String title) {
		
		List<Post> searchByTitle = postRepository.findByTitleContaining(title);
		
		//                                 this 	and                   this        should be same
		return searchByTitle.stream().map(search -> (this.modelMapper.map(search, PostDto.class))).collect(Collectors.toList());
	}
	
// ==================================Convert the Object===================================================
	
	// This helps us to convert the object
	
	// Converting the Post to postDto 
	public PostDto postTOpostDto(Post post) {
		
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		
		return postDto;
	}
	
	// Converting the PostDto to Post 
		public Post postDtoToPost(PostDto postDto) {
			
			Post post = this.modelMapper.map(postDto, Post.class);
			
			return post;
		}

}
