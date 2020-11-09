package com.app.bankingAPI_Spring.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

// User model class

public class User {
	 private int userId;
	 private String username;
	 @JsonInclude(Include.NON_NULL)
	 private String password;
	 private String firstName;
	 private String lastName;
	 private String email;
	 private Role role;
	 
	 public User() {
		 
	 }
	 
	 public User(int userId, String username, String password, String firstName, String lastName, String email,
			Role role) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(int role) {
		switch(role) {
			case 1:
				this.setRole(1, "Administrator");
				return;
			case 2:
				this.setRole(2, "Employee");
				return;
			case 3:
				this.setRole(3, "Premium");
				return;
			case 4:
				this.setRole(4, "Standard");
				return;
		}
	}
	
	private void setRole(int roleID, String role) {
		this.role = new Role();
		this.role.setRole(role);
		this.role.setRoleId(roleID);
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", role=" + role.getRole() + "]";
	}
	 
	
	 
	 
}
