package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.UserDto;

public interface UserServices {
	UserDto createUser(UserDto userDTO);
	UserDto updateUser(UserDto userDTO ,Integer UserId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);

}
