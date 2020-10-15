package com.app.banking_api.service.impl;

import com.app.banking_api.dao.BankingDAO;
import com.app.banking_api.dao.impl.BankingDAOImpl;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.Account;
import com.app.banking_api.service.BankService;

public class BankServiceImpl implements BankService {

	private BankingDAO dao = new BankingDAOImpl();
	@Override
	public Account performWithdrawal(int target, double amount) throws BusinessException {
		return dao.performWithdrawal(target, amount);
	}

	@Override
	public Account performDeposit(int target, double amount) throws BusinessException {
		// TODO Auto-generated method stub
		return dao.performDeposit(target, amount);
	}

	@Override
	public Account performTransfer(int to, int from, double amount) throws BusinessException {
		// TODO Auto-generated method stub
		return dao.performTransfer(to, from, amount);
	}

}
