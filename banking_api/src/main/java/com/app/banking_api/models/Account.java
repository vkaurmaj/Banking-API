package com.app.banking_api.models;

public class Account {
	private int accountId; // primary key
	private double balance;  // not null
	private AccountStatus status;
	private AccountType type;
	
	public Account() {
		
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	
	public void setStatus(int id, String status) {
		this.status = new AccountStatus();
		this.status.setStatus(status);
		this.status.setStatusId(id);
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}
	
	public void setType(int id, String type) {
		this.type = new AccountType();
		this.type.setType(type);
		this.type.setTypeId(id);
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", account-status="
				+ status.getStatus()+ ", account-type="+type.getType()+"]";
	}
	
	public boolean withdraw(double amount) {
		if (this.balance > amount) {
			this.balance -= amount;
			return true;
		} return false;
	}
	
	public void deposit(double amount) {
		this.balance += amount;
	}
	
	public boolean transfer(Account target, double amount) {
		if (this.balance > amount) {
			target.deposit(amount);
			this.withdraw(amount);
			return true;
		} return false;
	}
	
	
}
