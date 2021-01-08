package com.app.bankingAPI_Spring.daos;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.app.bankingAPI_Spring.models.Account;

// Database Access Objects for Accounts
	// 1. Outside resources and SQL statements
		// 1.1 Bank transaction SQL statements
		// 1.2 Account table access SQL statements
	// 2. Account table update methods
	// 3. Account table access methods

@Repository
public class AccountDAOImpl implements AccountDAO {

//________________________________________________________________________________________________________
// 1. Outside resources and SQL statements
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	private Logger logger = Logger.getLogger(AccountDAO.class.getName());
	
	// 1.1 Account table update SQL statements
	private final String WITHDRAW = "update accounts set balance = balance - ? where accID = ?";
	private final String DEPOSIT = "update accounts set balance = balance + ? where accID = ?";
	private final String INSERT_SQL = "insert into accounts(balance, statusID, typeID) values (?, ? ,?)";
	private final String INSERT_COMPLEMENT = "insert into user_accounts(userID, accID) values (?, ?)";
	
	// 1.2 Account table access SQL statements
	private final String FETCH_SQL = "select a.accID as accID, userID, balance, statusID, typeID "
									+ "from accounts a join user_accounts u on u.accID = a.accID";
	private final String BY_ID = " where u.accID = ?";
	private final String BY_STATUS = " where statusID = ?";
	private final String BY_UID = " where userID = ?";
	private final String UPDATE_SQL = "update accounts set balance = ?, statusID = ?, typeID = ? where accID = ?";
	
//________________________________________________________________________________________________________
// 2. Account table update methods
	
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
	
	public Account createAccount(int userID, Account acc) {
		logger.info("createAccount(): Creating new account...");
		KeyHolder holder = new GeneratedKeyHolder();
			jdbctemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setDouble(1, acc.getBalance());
					ps.setInt(2, acc.getStatus().getStatusId());
					ps.setInt(3, acc.getType().getTypeId());
					return ps;
				}
			}, holder);
			
		int newAccId = holder.getKey().intValue();
		jdbctemplate.update(INSERT_COMPLEMENT, userID, newAccId);
		logger.info("createUser(): New account created with ID #" + newAccId);
		acc.setAccountId(newAccId);
		return acc;
	}
	
	public Account updateAccount(Account acc) {
		logger.info("updateAccount(): Updating account...");
		int update;
		try {
			update = jdbctemplate.update(UPDATE_SQL, acc.getBalance(), acc.getStatus().getStatusId(),
										 acc.getType().getTypeId(), acc.getAccountId());
		} catch (DataAccessException e) {
			return null;
		}
		if (update == 1) {
			logger.info("updateAccount(): Account updated with ID #" + acc.getAccountId());
			return acc;
		}
		return null;
	}

//________________________________________________________________________________________________________
// 4. Account table access methods
	
	public List<Account> getAllAccounts() {
		try {
			logger.info("getAllAccounts(): Retrieving all accounts...");
			List<Account> rs = jdbctemplate.query(FETCH_SQL, new AccountMapper());
			logger.info("getAllAccounts(): All accounts retrieved");
			return rs;
		} catch (EmptyResultDataAccessException e) {
			logger.warning("getAllAccounts(): ERROR - No accounts found");
			return null;
		}
	}
	
	public Account getAccountById(int id) {
		try {
			logger.info("getAccountById(): Retrieving account by ID #" + id);
			Account rs = jdbctemplate.queryForObject(FETCH_SQL + BY_ID, new AccountMapper(), id);
			logger.info("getAccountById(): Account retrieved");
			return rs;
		} catch (EmptyResultDataAccessException e ) {
			logger.warning("getAccountById(): ERROR - Account not found");
			return null;
		}
	}
	
	public List<Account> getAccountsByStatus(int status) {
		try {
			logger.info("getAccountsByStatus(): Retrieving accounts by status ID #" + status);
			List<Account> rs = jdbctemplate.query(FETCH_SQL + BY_STATUS, new AccountMapper(), status);
			logger.info("getAccountsByStatus(): Accounts retrieved");
			return rs;
		} catch (EmptyResultDataAccessException e ) {
			logger.warning("getAccountsByStatus(): ERROR - No accounts with status ID #" + status);
			return null;
		}
	}
	
	public List<Account> getAccountsByUId(int user) {
		try {
			logger.info("getAccountsByUId(): Retrieving accounts by user ID #" + user);
			List<Account> rs = jdbctemplate.query(FETCH_SQL + BY_UID, new AccountMapper(), user);
			logger.info("getAccountsByUId(): Accounts retrieved");
			return rs;
		} catch (EmptyResultDataAccessException e ) {
			logger.warning("getAccountsByUId(): ERROR - No accounts with user ID #" + user);
			return null;
		}
	}


//________________________________________________________________________________________________________

}

// Helper class: Account object mapper
class AccountMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account acc = new Account();
		acc.setAccountId(rs.getInt("accID"));
		acc.setBalance(rs.getDouble("balance"));
		acc.setStatus(rs.getInt("statusID"));
		acc.setType(rs.getInt("typeID"));
		return acc;
	}
}
