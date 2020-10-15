package com.app.banking_api.service;

import java.util.ArrayList;

import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.Account;

public interface AccountService {

	public ArrayList<Account> getAccounts() throws BusinessException;
	
	public Account getAccountById(int id) throws BusinessException;
	
	public Account getAccountByUser(String user) throws BusinessException;
	
	public ArrayList<Account> getAccountsByStatus(String status) throws BusinessException;
}
