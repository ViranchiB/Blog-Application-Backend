package com.blog.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

	private long userId;
	private String msg;
	
	
	
	public ResourceNotFoundException(String msg, long userId) {
		super(String.format("%s : %s",msg,userId));
		this.userId = userId;
		this.msg = msg;
	}

}
