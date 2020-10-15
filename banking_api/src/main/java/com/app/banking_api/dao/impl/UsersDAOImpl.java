package com.app.banking_api.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.app.banking_api.dao.UsersDAO;
import com.app.banking_api.dbutil.MySqlConnection;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;

public class UsersDAOImpl implements UsersDAO {

	@Override
	public ArrayList<User> getAllUsers() throws BusinessException {
		ArrayList<User> users = new ArrayList<>();
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="SELECT * FROM users u JOIN roles r on r.roleID = u.roleID;";
			
			
			PreparedStatement preparedStatement=connection.prepareStatement(sql);

			ResultSet result=preparedStatement.executeQuery();

			while(result.next()) {
				User u = new User();
				u.setUserId(result.getInt("userID"));
				u.setUsername(result.getString("username"));
				u.setPassword(result.getString("pass"));
				u.setFirstName(result.getString("fname"));
				u.setLastName(result.getString("lname"));
				u.setEmail(result.getString("email"));
				u.setRole(result.getInt("roleID"), result.getString("roleName"));
				users.add(u);
			} 
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Username and/or email is already taken");
		}
		return users;
		
	}

	@Override
	public User getUserByID(int id) throws BusinessException {
		User user = new User();
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="SELECT * FROM users u JOIN roles r ON r.roleID = u.roleID WHERE u.userID=?;";
			
			
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet result=preparedStatement.executeQuery();
			
			if(result.next()) {
				user.setUserId(result.getInt("userID"));
				user.setUsername(result.getString("username"));
				user.setPassword(result.getString("pass"));
				user.setFirstName(result.getString("fname"));
				user.setLastName(result.getString("lname"));
				user.setEmail(result.getString("email"));
				user.setRole(result.getInt("roleID"), result.getString("roleName"));
			} else {
				throw new BusinessException("User not found");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Username and/or email is already taken");
		}
		return user;
	}


}
