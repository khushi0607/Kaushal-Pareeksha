package com.exam.service.implementation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	//Creating User
	@Override
	public User createUser(User user, Set<UserRole> userRoles)throws Exception {
		
		User temp=this.userRepository.findByUsername(user.getUsername());
		if(temp!=null)
		{
			System.out.println("User already present");
			throw new Exception("User already present");
		}
		else
		{
			for(UserRole ur: userRoles)
			{
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			temp=this.userRepository.save(user);
		}
		return temp;
	}

	@Override
	public User getUser(String username) {
		
		return this.userRepository.findByUsername(username);
	}
	
	@Override	
	public void deleteUserById(Long userId)
	{
		this.userRepository.deleteById(userId);
	}

}





