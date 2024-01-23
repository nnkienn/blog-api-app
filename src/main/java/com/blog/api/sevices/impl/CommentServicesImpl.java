package com.blog.api.sevices.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Comment;
import com.blog.api.entities.Post;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.CommentDto;
import com.blog.api.repositories.CommentRepo;
import com.blog.api.repositories.PostRepo;
import com.blog.api.services.CommentServices;

@Service
public class CommentServicesImpl implements CommentServices {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		Comment comment = this.modelmapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment save = this.commentRepo.save(comment);
		return this.modelmapper.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commnetId) {
		Comment com =this.commentRepo.findById(commnetId).orElseThrow(()->new ResourceNotFoundException("commnetId", "commnetId Id", commnetId));
		this.commentRepo.delete(com);
		
	}

}
