package com.app.bankingAPI_Spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bankingAPI_Spring.daos.AccountDAO;

@Service
public class AccountServiceImpl implements AccountService{


	@Autowired
	private AccountDAO accountDB;

	@Override
	public Boolean withdraw(int id, double amount) {
		// TODO Auto-generated method stub
		return accountDB.withdraw(id, amount);
	}

	@Override
	public Boolean deposit(int id, double amount) {
		// TODO Auto-generated method stub
		return accountDB.deposit(id, amount);
	}

	@Override
	public Boolean transfer(int sender, int receiver, double amount) {
		// TODO Auto-generated method stub
		return accountDB.transfer(sender, receiver, amount);
	}

}
