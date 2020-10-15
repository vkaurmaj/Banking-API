package com.app.banking_api.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.banking_api.dao.BankingDAO;
import com.app.banking_api.dbutil.MySqlConnection;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.Account;

public class BankingDAOImpl implements BankingDAO {

	@Override
	public Account performWithdrawal(int target, double amount) throws BusinessException {
		Account acct = new Account();
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="select * "
					+ "from accounts a join users u on u.userID = a.userID "
					+ "where accountID=?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, target);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				acct.setAccountId(resultSet.getInt(1));
				acct.setBalance(resultSet.getDouble(5));
				boolean withdraw = acct.withdraw(amount);
				if (withdraw) {
					sql = "update accounts set balance=? where accountID=?;";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setDouble(1, acct.getBalance());
					preparedStatement.setInt(2, acct.getAccountId());
					if (preparedStatement.executeUpdate() > 0) {
						return acct;
					} 	
				} else {
					throw new BusinessException("Account has insufficient funds");
				}
			} else {
				throw new BusinessException("Unable to find account");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured... Kindly contact SYSADMIN.....");
		}
		
		return null;
	}

	@Override
	public Account performDeposit(int target, double amount) throws BusinessException {
		Account acct = new Account();
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="select * "
					+ "from accounts a join users u on u.userID = a.userID "
					+ "where accountID=?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, target);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				acct.setAccountId(resultSet.getInt(1));
				acct.setBalance(resultSet.getDouble(5));
				acct.deposit(amount);
				sql = "update accounts set balance=? where accountID=?;";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setDouble(1, acct.getBalance());
				preparedStatement.setInt(2, acct.getAccountId());	
				if (preparedStatement.executeUpdate() > 0) {
					return acct;
				} 	
			}
			else {
				throw new BusinessException("Unable to find account");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured... Kindly contact SYSADMIN.....");
		}	
		
		return null;
	}

	@Override
	public Account performTransfer(int to, int from, double amount) throws BusinessException {
		Account toAcct = new Account();
		Account fromAcct = new Account();
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="select * "
					+ "from accounts a join users u on u.userID = a.userID "
					+ "where accountID=?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, from);
			ResultSet resultSet1=preparedStatement.executeQuery();
			
			sql="select * from accounts a join users u on u.userID = a.userID "
					+ "where accountID=?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, to);
			ResultSet resultSet2=preparedStatement.executeQuery();

			if(resultSet1.next() && resultSet2.next()) {
				fromAcct.setAccountId(resultSet1.getInt(1));
				fromAcct.setBalance(resultSet1.getDouble(5));
				toAcct.setAccountId(resultSet2.getInt(1));
				toAcct.setBalance(resultSet2.getDouble(5));
				boolean transfer = fromAcct.transfer(toAcct, amount);
				if (transfer) {
					sql = "update accounts set balance=? where accountID=?;";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setDouble(1, fromAcct.getBalance());
					preparedStatement.setInt(2, fromAcct.getAccountId());
					if (preparedStatement.executeUpdate() > 0) {
						return fromAcct;
					} 	
				} else {
					throw new BusinessException("Sending account has insufficient funds");
				}

			}else {
				throw new BusinessException("Unable to find sender and/or reciever account");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured... Kindly contact SYSADMIN.....");
		}	
		return null;
	}

	

}
