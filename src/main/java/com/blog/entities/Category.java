package com.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int categoryId;
	
	@NotEmpty(message = "Title should have min of 5 char.")
	@Size(min = 5, message = "Title should have min of 5 char.")
	private String categoryTitle;
	
	@NotEmpty(message = "Description should have min of 10 char.")
	@Size(min = 10, message = "Description should have min of 10 char.")
	private String categoryDescription;
}
