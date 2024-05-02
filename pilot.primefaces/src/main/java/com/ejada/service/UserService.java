package com.ejada.service;

import java.util.List;
import java.util.Optional;

import com.ejada.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import com.ejada.model.User;

public class UserService implements IUserService {

	
	private UserRepository userRepo;


	@Override
	public User login(String email, String password) {
		try {
			// put your validations before calling DAO
			 String salt = BCrypt.gensalt();

		        // Hash the password with the salt
		    
			User user = userRepo.findByEmail(email);
			if(user != null) {
				 if (BCrypt.checkpw(password, user.getPassword())) {
						return user;

			        } else {
			           return null;
			        }
			}
			return null;
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;

		}
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		try {
			// put your validations before calling DAO
			List<User> users = userRepo.findAll();
			return users;
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;

		}
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		try {
			// put your validations before calling DAO
			Optional<User> user = userRepo.findById(id);
			return user.get();
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;

		}
	}

	@Override
	public User signup(User user) {
		// TODO Auto-generated method stub
		try {
			// put your validations before calling DAO
			 String salt = BCrypt.gensalt();
		        // Hash the password with the salt
		    String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
		    user.setPassword(hashedPassword);
			user = userRepo.save(user);
			return user;
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;

		}
	}

}
