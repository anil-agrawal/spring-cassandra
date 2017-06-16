package com.san.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.san.domain.User;
import com.san.repo.OtherUserRepository;
import com.san.repo.UserRepository;

@Component
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OtherUserRepository otherUserRepository;

	public String insertRecord() {
		User user = new User("xyz name", "xyz@interra.com", "xyz");
		user.setId("1");
		user.setPassword("password");
		user = userRepository.save(user);
		return user.getId();
	}
	
	public User fetchUser(String id){
		return userRepository.findOne(id);
	}
}
