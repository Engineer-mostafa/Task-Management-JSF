package com.ejada.service;

import java.util.List;

import com.ejada.model.User;

public interface IUserService {
	
	User login(String email , String password);
	List<User> getAllUsers();
	User getUserById(Long id);
	User signup(User user);

}
