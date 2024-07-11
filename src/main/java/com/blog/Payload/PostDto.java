package com.blog.Payload;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private int postId;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	
	
	// Helps at the time of fetch data User and category is also attached with JSON response.
	private UserDto user;
	private CategoryDto category;
	
}
