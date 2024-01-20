package com.blog.api.sevices.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.repositories.CategoryRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.PostServices;


@Service
public class postServicesImp implements PostServices {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired 
	private UserRepo userRepo;
	
	
	@Autowired 
	private CategoryRepo categoryRepo;
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		
		Post post = this.modelMapper.map(postDto,Post.class);
		post.setImageName("defaut.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		// TODO Auto-generated method stub
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post update = this.postRepo.save(post);
		return this.modelMapper.map(update, PostDto.class);
		
	}

	@Override
	public void deletePost(Integer postId) {
		Post post= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		this.postRepo.delete(post);
		// TODO Auto-generated method stub

	}

	@Override
	public PostResponse getAllPost(Integer pagenumber,Integer pagesize,String sortby,String sortDir ){
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortby).ascending();
		}
		else {
			sort = Sort.by(sortby).descending();

		}
		
		org.springframework.data.domain.Pageable p = PageRequest.of(pagenumber, pagesize,sort);
		Page<Post> pagePost=this.postRepo.findAll(p);
		
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((cat)->this.modelMapper.map(cat, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setPostContent(postDtos);
		postResponse.setPagenumber(pagePost.getNumber());
		postResponse.setPagesize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post cat = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("postId","post Id",postId));
		return this.modelMapper.map(cat, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("userId", "userId id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		

		
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}
	
	@Override
	public
	List<PostDto> searchPost(String keyword){
		List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postdtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postdtos;
	}

}
