package com.app.banking_api.service.impl;

import java.util.ArrayList;

import com.app.banking_api.dao.AccountDAO;
import com.app.banking_api.dao.impl.AccountDAOImpl;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.Account;
import com.app.banking_api.service.AccountService;

public class AccountServiceImpl implements AccountService {

	AccountDAO dao = new AccountDAOImpl();
	@Override
	public ArrayList<Account> getAccounts() throws BusinessException {
		// TODO Auto-generated method stub
		ArrayList<Account> ans = dao.getAccounts();
		System.out.println(ans);
		return ans;
	}

	@Override
	public Account getAccountById(int id) throws BusinessException {
		// TODO Auto-generated method stub
		return dao.getAccountById(id);
	}

	@Override
	public Account getAccountByUser(String user) throws BusinessException {
		// TODO Auto-generated method stub
		return dao.getAccountByUser(user);
	}

	@Override
	public ArrayList<Account> getAccountsByStatus(String status) throws BusinessException {
		ArrayList<Account> ans = dao.getAccountsByStatus(status);
		return ans;
	}


	



}
