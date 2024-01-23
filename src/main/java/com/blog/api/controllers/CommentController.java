package com.blog.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.CommentDto;
import com.blog.api.services.CommentServices;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired
	private CommentServices commentServices;
	
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,@PathVariable Integer postId){
		CommentDto create = this.commentServices.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(create,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer postId){
		this.commentServices.deleteComment( postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("delete sucess",true),HttpStatus.OK);
	}
}
