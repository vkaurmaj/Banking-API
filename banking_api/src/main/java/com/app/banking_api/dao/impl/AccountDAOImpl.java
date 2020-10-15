package com.app.banking_api.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.app.banking_api.dao.AccountDAO;
import com.app.banking_api.dbutil.MySqlConnection;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.Account;

public class AccountDAOImpl implements AccountDAO {
	
	@Override
	public ArrayList<Account> getAccounts() throws BusinessException {
		ArrayList<Account> accts = new ArrayList<>();
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="SELECT a.accountID, a.balance, s.statusID, s.statName, t.typeID, t.accType\n"
					+ "FROM accounts a\n"
					+ "JOIN account_status s ON a.statusID = s.statusID\n"
					+ "JOIN account_type t ON a.typeID = t.typeID";
			
			
			PreparedStatement preparedStatement=connection.prepareStatement(sql);

			ResultSet result=preparedStatement.executeQuery();

			while(result.next()) {
				Account a = new Account();
				a.setAccountId(result.getInt("accountID"));
				a.setBalance(result.getDouble("balance"));
				a.setStatus(result.getInt("statusID"), result.getString("statName"));
				a.setType(result.getInt("typeID"), result.getString("accType"));
				accts.add(a);

			} 
			
		} catch (ClassNotFoundException e1) {
			throw new BusinessException("Internal error: class not found ");

		} catch (SQLException e2) {
			throw new BusinessException("Internal error: SQL ");
		}
		return accts;
	}

	@Override
	public Account getAccountById(int id) throws BusinessException {
		Account acct = new Account();
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="SELECT a.accountID, a.balance, s.statusID, s.statName, t.typeID, t.accType\n"
					+ "FROM accounts a\n"
					+ "JOIN account_status s ON a.statusID = s.statusID\n"
					+ "JOIN account_type t ON a.typeID = t.typeID\n"
					+ "WHERE accountID=?";
			
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet result=preparedStatement.executeQuery();

			if (result.next()) {
				acct.setAccountId(result.getInt("accountID"));
				acct.setBalance(result.getDouble("balance"));
				acct.setStatus(result.getInt("statusID"), result.getString("statName"));
				acct.setType(result.getInt("typeID"), result.getString("accType"));
			} else {
				throw new BusinessException("Account not found");
			}
			
		} catch (ClassNotFoundException e1) {
			throw new BusinessException("Internal error: class not found ");

		} catch (SQLException e2) {
			throw new BusinessException("Internal error: SQL ");
		}
		
		return acct;
	}

	@Override
	public Account getAccountByUser(String user) throws BusinessException {
		Account acct = new Account();
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="SELECT a.accountID, a.balance, s.statusID, s.statName, t.typeID, t.accType\n"
					+ "FROM accounts a\n"
					+ "JOIN account_status s ON a.statusID = s.statusID\n"
					+ "JOIN account_type t ON a.typeID = t.typeID\n"
					+ "JOIN users u ON u.userID=a.userID\n"
					+ "WHERE username=?";
			
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, user);
			ResultSet result=preparedStatement.executeQuery();

			if (result.next()) {
				acct.setAccountId(result.getInt("accountID"));
				acct.setBalance(result.getDouble("balance"));
				acct.setStatus(result.getInt("statusID"), result.getString("statName"));
				acct.setType(result.getInt("typeID"), result.getString("accType"));
			} else {
				throw new BusinessException("Account not found");
			}
			
		} catch (ClassNotFoundException e1) {
			throw new BusinessException("Internal error: class not found ");

		} catch (SQLException e2) {
			throw new BusinessException("Internal error: SQL ");
		}
		
		return acct;
	}

	@Override
	public ArrayList<Account> getAccountsByStatus(String status) throws BusinessException {
		ArrayList<Account> accts = new ArrayList<>();
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="SELECT a.accountID, a.balance, s.statusID, s.statName, t.typeID, t.accType\n"
					+ "FROM accounts a\n"
					+ "JOIN account_status s ON a.statusID = s.statusID\n"
					+ "JOIN account_type t ON a.typeID = t.typeID\n"
					+ "WHERE statName=?";
			
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, status);
			ResultSet result=preparedStatement.executeQuery();

			while(result.next()) {
				Account a = new Account();
				a.setAccountId(result.getInt("accountID"));
				a.setBalance(result.getDouble("balance"));
				a.setStatus(result.getInt("statusID"), result.getString("statName"));
				a.setType(result.getInt("typeID"), result.getString("accType"));
				accts.add(a);
			} 
			
		} catch (ClassNotFoundException e1) {
			throw new BusinessException("Internal error: class not found ");

		} catch (SQLException e2) {
			throw new BusinessException("Internal error: SQL ");
		}
		
		return accts;
	}
}
