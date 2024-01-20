package com.blog.api.sevices.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.UserDto;
import com.blog.api.repositories.UserRepo;
import com.blog.api.services.UserServices;

@Service
public class UserServicesImpl implements UserServices {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelmapper;
	@Override
	public UserDto createUser(UserDto userDTO) {
		User user = this.dtotoUser(userDTO);
		User createUser =this.userRepo.save(user);
		return this.UsertoDto(createUser);
	}
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow((() -> new ResourceNotFoundException("User id", "user", userId)));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updateduser = this.userRepo.save(user);
		UserDto userDto1= this.UsertoDto(updateduser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow((() -> new ResourceNotFoundException("User id", "user", userId)));
		return this.UsertoDto(user);
	}
	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user-> this.UsertoDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow((() -> new ResourceNotFoundException("User id", "user", userId)));
		this.userRepo.delete(user);
	}
	
	public User dtotoUser(UserDto userDto) {
		
		User user = this.modelmapper.map(userDto, User.class);
//		User user = new User();
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(user.getPassword());
		return user;
		
	}
	public UserDto UsertoDto(User user) {
		
		UserDto userDTO = this.modelmapper.map(user, UserDto.class);
//		UserDto dtotoUser = new UserDto();
//		dtotoUser.setId(user.getId());
//		dtotoUser.setName(user.getName());
//		dtotoUser.setEmail(user.getEmail());
//		dtotoUser.setPassword(user.getPassword());
		
		
		return userDTO;
		
	}
	

	
}
