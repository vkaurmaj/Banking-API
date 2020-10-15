package com.app.banking_api.service.impl;

import java.util.ArrayList;

import com.app.banking_api.dao.UsersDAO;
import com.app.banking_api.dao.impl.UsersDAOImpl;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;
import com.app.banking_api.service.UserService;

public class UserServiceImpl implements UserService {

	UsersDAO dao = new UsersDAOImpl();
	@Override
	public ArrayList<User> getAllUsers() throws BusinessException {
		// TODO Auto-generated method stub
		return dao.getAllUsers();
	}

	@Override
	public User getUserByID(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return dao.getUserByID(id);
	}

}
