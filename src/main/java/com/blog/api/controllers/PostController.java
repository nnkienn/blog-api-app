package com.blog.api.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.entities.Post;
import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.payloads.UserDto;
import com.blog.api.services.FileServices;
import com.blog.api.services.PostServices;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/")
public class PostController {
	@Autowired
	private PostServices postServices;

	@Autowired
	private FileServices fileServices;

	@Value("${project.image}")
	private String Path;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto createPost = this.postServices.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getpostByUser(@PathVariable Integer userId) {
		List<PostDto> posts = this.postServices.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getpostByposts(@PathVariable Integer categoryId) {
		List<PostDto> posts = this.postServices.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getallpost(
			@RequestParam(value = "pagenumber", defaultValue = "0", required = false) Integer pagenumber,
			@RequestParam(value = "pagesize", defaultValue = "10", required = false) Integer pagesize,
			@RequestParam(value = "sortBy", defaultValue = "10", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PostResponse posts = this.postServices.getAllPost(pagenumber, pagesize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);

	}

	@GetMapping("{postId}/posts")
	public ResponseEntity<PostDto> getpostbyId(@PathVariable Integer postId) {
		PostDto posts = this.postServices.getPostById(postId);
		return ResponseEntity.ok(posts);

	}

	@DeleteMapping("{postId}/posts")
	public ResponseEntity<ApiResponse> deletepost(@PathVariable Integer postId) {
		this.postServices.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post delete Sucessfully", true), HttpStatus.OK);

	}

	@PutMapping("{postId}/posts")
	public ResponseEntity<PostDto> updatepost(@RequestBody PostDto postdto, @PathVariable("postId") Integer postId) {
		PostDto updatepost = this.postServices.updatePost(postdto, postId);
		return new ResponseEntity<PostDto>(updatepost, HttpStatus.OK);
	}

	@GetMapping("posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchpostbytitle(@PathVariable("keyword") String keyword) {
		List<PostDto> result = this.postServices.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}

	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImg(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		PostDto postDto = this.postServices.getPostById(postId);

		String fileName = this.fileServices.uploadImage(Path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postServices.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_PNG_VALUE)
    public void downloadImage(@PathVariable String imageName,
                              HttpServletResponse response) throws IOException {
    InputStream resource=this.fileServices.getResource(Path,imageName);
    response.setContentType(MediaType.IMAGE_PNG_VALUE);
    org.springframework.util.StreamUtils.copy(resource,response.getOutputStream());

    }


}
