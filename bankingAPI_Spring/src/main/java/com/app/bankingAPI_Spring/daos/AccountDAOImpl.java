package com.app.bankingAPI_Spring.daos;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// Database Access Objects for Accounts
	// 1. Outside resources and SQL statements
	// 2. Database update methods

@Repository
public class AccountDAOImpl implements AccountDAO {

//________________________________________________________________________________________________________
// 1. Outside resources and SQL statements
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	private Logger logger = Logger.getLogger(AccountDAO.class.getName());
	
	private final String WITHDRAW = "update accounts set balance = balance - ? where accID = ?";
	private final String DEPOSIT = "update accounts set balance = balance + ? where accID = ?";
	
//________________________________________________________________________________________________________
// 2. Database update methods
	
	@Override
	public Boolean withdraw(int id, double amount) {
		logger.info("withdraw(): Withdrawing from ID #" + id + " the amount $" + amount);
		if (jdbctemplate.update(WITHDRAW, amount, id) == 1) {
			logger.info("withdraw(): Transaction complete");
			return true;
		}
		else {
			logger.warning("withdraw(): Transaction failed");
			return false;
		}
	}

	@Override
	public Boolean deposit(int id, double amount) {
		logger.info("deposit(): Depositing to ID #" + id + " the amount $" + amount);
		// TODO Auto-generated method stub
		if (jdbctemplate.update(DEPOSIT, amount, id) == 1) {
			logger.info("deposit(): Transaction complete");
			return true;
		}
		else {
			logger.warning("deposit(): Transaction failed");
			return false;
		}
	}

	@Override
	public Boolean transfer(int sender, int receiver, double amount) {
		logger.info("transfer(): Transferring from ID #" + sender + " the amount $" + amount + " to ID #" + receiver);
		// TODO Auto-generated method stub
		
		if (this.withdraw(sender, amount)) {
			if (this.deposit(receiver, amount)) { 
				logger.info("transfer(): Transaction complete");
				return true;
			} else {
				this.deposit(sender, amount);
			}
		}
		
		logger.warning("transfer(): Transaction failed");
		return false;
	}

//________________________________________________________________________________________________________


}
