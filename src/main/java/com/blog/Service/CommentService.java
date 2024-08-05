package com.blog.Service;

import com.blog.Payload.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	void deleteCommentById(Integer commentId);
}
