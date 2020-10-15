package com.app.banking_api.dao;

import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;

public interface LoginDAO {


	public int isValidUserCredentials(User user) throws BusinessException;


}
