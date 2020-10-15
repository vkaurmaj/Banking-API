package com.app.banking_api.service.impl;

import com.app.banking_api.dao.LoginDAO;
import com.app.banking_api.dao.impl.LoginDAOImpl;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;
import com.app.banking_api.service.LoginService;

public class LoginServiceImpl implements LoginService{

	private LoginDAO dao= new LoginDAOImpl();
	
	public int isValidUserCredentials(User user) throws BusinessException {
		int b = -1;
		
		if(user!=null && user.getUsername()!=null && user.getPassword()!=null 
				&& user.getUsername().matches("[a-z]{3,6}[0-9]{3,4}") 
				&& user.getPassword().matches("[a-z]{3,6}[*#$@]{1}[0-9]{3,4}")) {
			
			b= dao.isValidUserCredentials(user);
			
		}else {
			throw new BusinessException("Invalid Login Credentials");
		}
		return b;
	}

}
