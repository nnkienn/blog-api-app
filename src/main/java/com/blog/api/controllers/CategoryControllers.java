package com.blog.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.entities.Category;
import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.CategoryDto;
import com.blog.api.services.CategoryServices;

@RestController
@RequestMapping("api/categories")
public class CategoryControllers {
	@Autowired
	private CategoryServices categoriesServices;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCate(@RequestBody CategoryDto categoryDto){
		CategoryDto createCategory = this.categoriesServices.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> upCate(@RequestBody CategoryDto categoryId , @PathVariable Integer catId){
		CategoryDto updateCategory = this.categoriesServices.updateCategory(categoryId,catId);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCate(@PathVariable Integer catId){
		this.categoriesServices.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("detele sucess",true),HttpStatus.OK);
	}
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCate( @PathVariable Integer catId){
		CategoryDto Category = this.categoriesServices.getCategory(catId);
		return new ResponseEntity<CategoryDto>(Category,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getallCate(){
		List<CategoryDto> categories = this.categoriesServices.getCategory();
		return ResponseEntity.ok(categories);
	}
	

	

}
