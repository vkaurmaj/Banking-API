package com.app.bankingAPI_Spring.controllers;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bankingAPI_Spring.models.Message;
import com.app.bankingAPI_Spring.models.User;
import com.app.bankingAPI_Spring.services.UserService;

// Controller for User related API calls
	// 1. Outside resources
	// 2. Accessing/updating user data

@RestController
@RequestMapping("/users")
public class UserController {

//________________________________________________________________________________________________________
// 1. Outside resources
	
	@Autowired
	private UserService usersDB;
	private Logger logger = Logger.getLogger(UserController.class.getName());

//________________________________________________________________________________________________________
// 2. Accessing/updating user data
	
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
		return new ResponseEntity<Object>(new Message("Unauthorized access"), HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("{id}")
	public ResponseEntity<Object> getUserByID(@PathVariable("id") Integer id, HttpSession session) {
		logger.info("getUserByID(): Getting user by ID #" + id +"...");
		Integer sId = (Integer) session.getAttribute("ID");
		String role = (String) session.getAttribute("ROLE");
		if (sId != null && role != null) {
			if (role.equals("Administrator") || role.equals("Employee") || sId.equals(id)) {
				User user = usersDB.getUserById(id);
				if (user != null) {
					logger.info("getUserByID(): User retrieved successfully");
					return ResponseEntity.ok(user);
				}
				logger.warning("getUserByID(): ERROR - User not found");
				return new ResponseEntity<Object>(new Message("User not found"), HttpStatus.NOT_FOUND);
			}
			logger.warning("getUserByID(): ERROR - Unauthorized access");
			return new ResponseEntity<Object>(new Message("Unauthorized access"), HttpStatus.UNAUTHORIZED);
		}
		logger.warning("getUserByID(): ERROR - Unauthorized access: requires login");
		return ResponseEntity.badRequest().body(new Message("Unauthorized access: requires login"));
	}
	
	@PutMapping("{id}/update")
	public ResponseEntity<Object> updateUser(@PathVariable("id") Integer id, @RequestBody User user, HttpSession session) {
		logger.info("updateUser(): Updating user...");
		Integer sId = (Integer) session.getAttribute("ID");
		String role = (String) session.getAttribute("ROLE");		
		if (sId != null && role != null) {
			if (role.equals("Administrator") || sId.equals(id)) {
				user.setUserId(id);
				User updated = usersDB.updateUser(user);
				if (updated != null) {
					logger.info("updateUser(): User updated successfully");
					return ResponseEntity.ok(usersDB.getUserById(id));
				}
				logger.warning("updateUser(): ERROR - Update failed");
				return ResponseEntity.badRequest().body(new Message("Update failed"));
			}
			logger.warning("updateUser(): ERROR - Unauthorized access");
			return new ResponseEntity<Object>(new Message("Unauthorized access"), HttpStatus.UNAUTHORIZED);
		}
		logger.warning("updateUser(): ERROR - Unauthorized access: requires login");
		return ResponseEntity.badRequest().body(new Message("Unauthorized access: requires login"));
	}

//________________________________________________________________________________________________________
	
}
