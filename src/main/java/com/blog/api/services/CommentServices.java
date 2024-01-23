package com.blog.api.services;

import com.blog.api.payloads.CommentDto;

public interface CommentServices  {
	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commnetId);
}
