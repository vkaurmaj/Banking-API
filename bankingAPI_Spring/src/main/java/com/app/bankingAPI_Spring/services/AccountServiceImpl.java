package com.app.bankingAPI_Spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bankingAPI_Spring.daos.AccountDAO;
import com.app.bankingAPI_Spring.models.Account;

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

	@Override
	public Account createAccount(int userID, Account acc) {
		// TODO Auto-generated method stub
		return accountDB.createAccount(userID, acc);
	}

	@Override
	public Account updateAccount(Account acc) {
		// TODO Auto-generated method stub
		return accountDB.updateAccount(acc);
	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return accountDB.getAllAccounts();
	}

	@Override
	public Account getAccountById(int id) {
		// TODO Auto-generated method stub
		return accountDB.getAccountById(id);
	}

	@Override
	public List<Account> getAccountsByStatus(int status) {
		// TODO Auto-generated method stub
		return accountDB.getAccountsByStatus(status);
	}

	@Override
	public List<Account> getAccountsByUId(int userId) {
		// TODO Auto-generated method stub
		return accountDB.getAccountsByUId(userId);
	}

}
