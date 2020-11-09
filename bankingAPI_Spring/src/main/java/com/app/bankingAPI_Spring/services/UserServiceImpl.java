package com.app.bankingAPI_Spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bankingAPI_Spring.daos.UserDAO;
import com.app.bankingAPI_Spring.models.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO usersDB;
	
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return usersDB.getAllUsers();
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return usersDB.getUserById(id);
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return usersDB.updateUser(user);
	}

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		return usersDB.createUser(user);
	}

	@Override
	public User loginUser(String username, String pword) {
		// TODO Auto-generated method stub
		return usersDB.loginUser(username, pword);
	}

}
