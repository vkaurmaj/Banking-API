package com.app.bankingAPI_Spring.daos;

public interface AccountDAO {

	public Boolean withdraw(int id, double amount);
	public Boolean deposit(int id, double amount);
	public Boolean transfer(int sender, int receiver, double amount);
	
}
