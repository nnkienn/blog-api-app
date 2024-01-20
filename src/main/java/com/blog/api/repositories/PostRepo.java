package com.blog.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
	
}
