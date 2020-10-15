package com.app.banking_api.service;

import java.util.ArrayList;

import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;

public interface UserService {

	public ArrayList<User> getAllUsers() throws BusinessException;
	
	public User getUserByID(int id) throws BusinessException; 
}
