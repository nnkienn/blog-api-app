package com.blog.api.payloads;

import java.util.Date;

import com.blog.api.entities.Category;
import com.blog.api.entities.User;

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
	private Date addDate;
	private UserDto user;
	private CategoryDto category;
}
