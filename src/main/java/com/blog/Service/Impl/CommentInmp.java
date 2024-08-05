package com.blog.Service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Exception.ResourceNotFoundException;
import com.blog.Payload.CommentDto;
import com.blog.Service.CommentService;
import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;

@Service
public class CommentInmp implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		// 1. Get a post by ID
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found along with the ID ", postId));
				
		// 2. COnvert dto to normal
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
		// 3. Set the post to comment
		comment.setPost(post);
		
		// 3. Store it in SQL
		Comment savedComment = commentRepository.save(comment);
		
		// 3. Return DTO object
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteCommentById(Integer commentId) {
		
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found along with the ID ", commentId));
		
		commentRepository.delete(comment);
	}

}
