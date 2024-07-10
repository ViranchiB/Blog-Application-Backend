package com.blog.Payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	private int id;
	private String name;
	private String email;
	private String password;
	private String about;
	
}
