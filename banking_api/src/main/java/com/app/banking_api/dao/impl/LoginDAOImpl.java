package com.app.banking_api.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.banking_api.dao.LoginDAO;
import com.app.banking_api.dbutil.MySqlConnection;
import com.app.banking_api.exception.BusinessException;
import com.app.banking_api.models.User;

public class LoginDAOImpl implements LoginDAO {

	public int isValidUserCredentials(User user) throws BusinessException {
		int b=-1;
		
		try(Connection connection=MySqlConnection.getConnection()){
			String sql="select * from users where username=? and pass=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				b=resultSet.getInt("roleID");
			}else {
				throw new BusinessException("Invalid Login Credentials");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); //this line you should take it off before going live(production)
			throw new BusinessException("Internal error occured... Kindly contact SYSADMIN.....");
		}
		
		return b;
	}

}