package com.app.bankingAPI_Spring.daos;

import java.util.List;

import com.app.bankingAPI_Spring.models.Account;

public interface AccountDAO {

	public Boolean withdraw(int id, double amount);
	public Boolean deposit(int id, double amount);
	public Boolean transfer(int sender, int receiver, double amount);
	public Account createAccount(int userID, Account acc);
	public Account updateAccount(Account acc);
	public List<Account> getAllAccounts();
	public Account getAccountById(int id);
	public List<Account> getAccountsByStatus(int status);
	public List<Account> getAccountsByUId(int userId);
	
	
	
}
