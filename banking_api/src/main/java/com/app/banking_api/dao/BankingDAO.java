package com.app.banking_api.dao;

import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.Account;

public interface BankingDAO {
	
	public Account performWithdrawal(int target, double amount) throws BusinessException;
	
	public Account performDeposit(int target, double amount) throws BusinessException;

	public Account performTransfer(int to, int from, double amount) throws BusinessException;

}
