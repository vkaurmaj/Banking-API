package com.app.bankingAPI_Spring.services;

import java.util.List;

import com.app.bankingAPI_Spring.models.User;

public interface UserService {
	
	public List<User> getAllUsers();
	public User getUserById(int id);
	public User updateUser(User user);
	public User createUser(User user);
	public User loginUser(String username, String pword);

}
