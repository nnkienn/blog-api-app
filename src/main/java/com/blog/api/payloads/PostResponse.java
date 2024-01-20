package com.blog.api.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	private List<PostDto> postContent;
	private int pagenumber;
	private int pagesize;
	private long totalElements;
	private int totalPages;
	private boolean LastPage;
	

}
