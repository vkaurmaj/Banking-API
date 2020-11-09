package com.app.bankingAPI_Spring.controllers;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bankingAPI_Spring.models.Message;
import com.app.bankingAPI_Spring.services.UserService;

// Controller for User related API calls
	// 1. Outside resources

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService usersDB;
	private Logger logger = Logger.getLogger(UserController.class.getName());
	
	@GetMapping()
	public ResponseEntity<Object> getAllUsers(HttpSession session) {
		logger.info("getAllUsers(): Attempting to get all users...");
		String role = (String) session.getAttribute("ROLE");
		if (role != null) {
			if (role.equals("Administrator") || role.equals("Employee")) {
				logger.info("getAllUsers(): Permissions verified, returning users...");
				return ResponseEntity.ok(usersDB.getAllUsers());
			}
		}
		logger.warning("getAllUsers(): Unauthorized user access");
		return new ResponseEntity<Object>(new Message("Unauthorized user"), HttpStatus.UNAUTHORIZED);
	}
	
	
	
}
