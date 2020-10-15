package com.app.banking_api.service.impl;

import com.app.banking_api.dao.RegisterDAO;
import com.app.banking_api.dao.impl.RegisterDAOImpl;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;
import com.app.banking_api.service.RegisterService;

public class RegisterServiceImpl implements RegisterService {

	private RegisterDAO dao = new RegisterDAOImpl();
	
	@Override
	public boolean isValidUserCredentials(User user) throws BusinessException {
		boolean b = false;
		
		if(user!=null && user.getUsername()!=null && user.getPassword()!=null 
				&& user.getUsername().matches("[a-z]{3,6}[0-9]{3,4}") 
				&& user.getPassword().matches("[a-z]{3,6}[*#$@]{1}[0-9]{3,4}")) {
			
			b = dao.isValidUserCredentials(user);
			
		}else {
			throw new BusinessException("Invalid field inputs for user and/or password");
		}
		return b;
	}

}
