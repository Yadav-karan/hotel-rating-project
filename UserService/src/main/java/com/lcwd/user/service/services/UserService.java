package com.lcwd.user.service.services;

import java.util.List;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;

public interface UserService {

	User saveUser(User user);
	
	List<User> getAllUsers();
	
	User getUser(String userId) throws ResourceNotFoundException;
	
}
