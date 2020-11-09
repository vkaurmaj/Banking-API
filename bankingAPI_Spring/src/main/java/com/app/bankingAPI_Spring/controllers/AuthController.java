package com.app.bankingAPI_Spring.controllers;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bankingAPI_Spring.models.Message;
import com.app.bankingAPI_Spring.models.User;
import com.app.bankingAPI_Spring.services.UserService;

// Controller for authentication API calls
	// 1. Outside resources
	// 2. Landing point for authentication requests

@RestController
public class AuthController {

//________________________________________________________________________________________________________
// 1. Outside resources
	
	@Autowired
	private UserService usersDB;
	private static Logger logger = Logger.getLogger(AuthController.class.getName());

	
	@GetMapping(path = "/test")
	public ResponseEntity<Object> test() {
		return ResponseEntity.ok(usersDB.getAllUsers());
	}

//________________________________________________________________________________________________________
// 2. Landing point for authentication requests
	
	// Login user
	@PostMapping(path = "/login")
	public ResponseEntity<Object> loginUser(@RequestBody User user, HttpServletRequest request) {

		logger.info("loginUser(): Requesting login service...");
		User loggedIn = usersDB.loginUser(user.getUsername(), user.getPassword());

		if (loggedIn != null) {
			String role = (String) request.getSession().getAttribute("ROLE");

			if (role == null) {
				request.getSession().setAttribute("ROLE", loggedIn.getRole().getRole());
				request.getSession().setAttribute("USER", loggedIn.getUserId());
				logger.info("loginUser(): Session Created");
				return ResponseEntity.ok(loggedIn);
			} else {
				request.getSession().invalidate();
				logger.warning("loginUser(): ERROR - There exists an old session (now removed)");
				return ResponseEntity.accepted().body(new Message("Try again"));
			}

		} else {
			return ResponseEntity.badRequest().body(new Message("Invalid Credentials"));

		}
	}

	// Logout User

	@PostMapping(path = "/logout")
	public ResponseEntity<Object> logoutUser(HttpSession session) {
		logger.info("logoutUser(): Attempting to log out user...");
		Integer user = (Integer) session.getAttribute("USER");
		if (user == null) {
			logger.warning("logoutUser(): ERROR - User has no active session");
			return ResponseEntity.badRequest().body(new Message("User not logged in"));
		}
		session.invalidate();
		logger.info("logoutUser(): User logged out");
		return ResponseEntity.ok(new Message("You have successfully logged out ID #" + user));
	}

//________________________________________________________________________________________________________
// Register user

	@PostMapping(path = "/register")
	public ResponseEntity<Object> registerUser(@RequestBody User user, HttpSession session) {
		logger.info("registerUser(): Attempting to register new user...");
		if (session.getAttribute("ROLE").equals("Administrator") || session.getAttribute("ROLE") == null) {
			User registered = usersDB.createUser(user);
			if (registered == null) {
				logger.warning("registerUser(): ERROR - Registration failed");
				return ResponseEntity.badRequest().body(new Message("Invalid Credentials"));
			}
			logger.info("registerUser(): Successfully registered user");
			return new ResponseEntity<Object>(registered, HttpStatus.CREATED);
		}
		logger.warning("registerUser(): Invalid permissions access attempted");
		return ResponseEntity.badRequest().body(new Message("Invalid permissions for registration"));
	}

//________________________________________________________________________________________________________

}
