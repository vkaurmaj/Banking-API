package com.app.bankingAPI_Spring.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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

import com.app.bankingAPI_Spring.models.User;

// Data Access Objects for Users
	// 1. Outside resources and SQL statements
		// 1.1 User table update SQL statements
		// 1.2 User table access SQL statements
	// 2. User table update methods
	// 3. User get methods and actions

@Repository
public class UserDAOImpl implements UserDAO {

//________________________________________________________________________________________________________
// 1. Outside resources and SQL statements
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	private Logger logger = Logger.getLogger(UserDAOImpl.class.getName());
	
	// 1.1 User table update SQL statements
	private final String UPDATE_SQL = "update users set username=?, pword=?, fname=?, lname=?, email=? where userId = ?";
	
	// 1.2 User table access SQL statements
	private final String INSERT_SQL = "INSERT INTO users(username, pword,fname, lname, email, roleID) values(?,?,?,?,?,?)";
	private final String FETCH_SQL = "select * from users r join user_roles u on r.roleID=u.roleID";
	private final String BY_ID = " where userID = ?";
	private final String BY_LOGIN = " where username=? and pword=?";

//________________________________________________________________________________________________________
// 2. User table update methods
	
	@Override
	public User updateUser(User user) {
		logger.info("updateUser(): Updating user...");
		int update;
		try {
			update = jdbctemplate.update(UPDATE_SQL, user.getUsername(), user.getPassword(), 
								user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserId());
		} catch (DataAccessException e) {
			logger.info("updateUser(): ERROR - SQL update failed");
			return null;
		}
		if (update == 1) {
			logger.info("updateUser(): User updated with ID #" + user.getUserId());
			return user;
		}
		return null;
	}

	@Override
	public User createUser(User user) {
		KeyHolder holder = new GeneratedKeyHolder();
		logger.info("createUser(): Creating new user...");
			jdbctemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, user.getUsername());
					ps.setString(2, user.getPassword());
					ps.setString(3, user.getFirstName());
					ps.setString(4, user.getLastName());
					ps.setString(5, user.getEmail());
					ps.setInt(6, user.getRole().getRoleId());
					return ps;
				}
			}, holder);
			
		int newUserId = holder.getKey().intValue();
		logger.info("createUser(): New user created with ID #" + newUserId);
		user.setUserId(newUserId);
		return user;
	}

//________________________________________________________________________________________________________
// 3. User access methods and actions
	
	@Override
	public List<User> getAllUsers() {
		try {
			logger.info("getAllUsers(): Retrieving all registered users....");
			return jdbctemplate.query(FETCH_SQL, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			logger.warning("getAllUsers(): ERROR - No users found");
			return null;
		}
	}

	@Override
	public User getUserById(int id) {
		try {
			logger.info("getUserById(): Retrieving specified user...");
			return jdbctemplate.queryForObject(FETCH_SQL + BY_ID, new UserMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			logger.warning("getUserById(): ERROR - user not found");
			return null;
		}
	}
	
	@Override
	public User loginUser(String username, String pword) {
		try {
			logger.info("loginUser(): Attempting to login user...");
			return jdbctemplate.queryForObject(FETCH_SQL + BY_LOGIN, new UserMapper(), username, pword);
		} catch (EmptyResultDataAccessException e) {
			logger.warning("loginUser(): ERROR - user not found");
			return null;
		}
	}

//________________________________________________________________________________________________________


} 

// Helper class: User object mapper
class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("userID"));
		user.setUsername(rs.getString("username"));
		user.setFirstName(rs.getString("fname"));
		user.setLastName(rs.getString("lname"));
		user.setEmail(rs.getString("email"));
		user.setRole(rs.getInt("roleID"));
		return user;
	}
}
