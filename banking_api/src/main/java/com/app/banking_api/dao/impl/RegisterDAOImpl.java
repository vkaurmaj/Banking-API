package com.app.banking_api.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.app.banking_api.dao.RegisterDAO;
import com.app.banking_api.dbutil.MySqlConnection;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;

public class RegisterDAOImpl implements RegisterDAO {

	@Override
	public boolean isValidUserCredentials(User user) throws BusinessException {
		boolean b = false;
		
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="INSERT INTO banking.users (username, pass, fname, lname, email, roleID) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			
			
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setInt(6, user.getRole().getRoleId());
			
			int result=preparedStatement.executeUpdate();
			
			if(result > 0) {
				b=true;
			} 
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); //this line you should take it off before going live(production)
			throw new BusinessException("Username and/or email is already taken");
		}
		
		return b;
	}

}
