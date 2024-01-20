package com.blog.api.services;

import java.util.List;

import com.blog.api.entities.Post;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;

public interface PostServices {
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	PostDto updatePost(PostDto postDto , Integer postId);
	void deletePost(Integer postId);
	PostResponse getAllPost(Integer pagenumber,Integer pagesize,String sortby,String sortDir);
	PostDto getPostById(Integer postId);
	List<PostDto> getPostsByCategory(Integer categoryId);
	List<PostDto> getPostsByUser(Integer userId);
	List<PostDto> searchPost(String keyword);
	
}
