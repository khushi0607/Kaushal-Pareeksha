package com.exam.service;

import java.util.Set;

import com.exam.model.User;
import com.exam.model.UserRole;

public interface UserService {
	
	//creating user and storing it in database
	public User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	//getting user from database
	public User getUser(String username);
	
	//deleting user from database using id
	public void deleteUserById(Long userId);

}
