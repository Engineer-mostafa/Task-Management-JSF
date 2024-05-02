package com.ejada.dao;

import java.util.List;
import java.util.Set;

import com.ejada.model.Task;
import com.ejada.model.User;

public interface IUserDAO {
	User save(User employee);
	User findById(Long id);
	User update(User employee);
	void deleteById(Long id);
	User findUserByEmail(String email);
	void delete(User user);
	List<User> findAll();
}
