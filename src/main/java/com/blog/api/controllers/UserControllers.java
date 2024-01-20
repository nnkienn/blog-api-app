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

import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.UserDto;
import com.blog.api.services.UserServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserControllers {
    @Autowired
    private UserServices userService;

    // Post
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }


	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto, @PathVariable("userId") Integer userId) {
		UserDto updateUser = this.userService.updateUser(userdto, userId);
		return ResponseEntity.ok(updateUser);

	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user delete Sucessfully", true), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getsingleUser(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));

	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser() {
		return ResponseEntity.ok(this.userService.getAllUsers());

	}
}
