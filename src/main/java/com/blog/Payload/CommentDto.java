package com.blog.Payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private int postId;
	private String commentMsg;
}
