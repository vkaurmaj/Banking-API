package com.app.banking_api.service;

import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;

public interface RegisterService {
	
	public boolean isValidUserCredentials(User user) throws BusinessException;

}
