package com.app.bankingAPI_Spring.controllers;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bankingAPI_Spring.models.Account;
import com.app.bankingAPI_Spring.models.Message;
import com.app.bankingAPI_Spring.models.TransDetails;
import com.app.bankingAPI_Spring.services.AccountService;

// Controller for Account related API calls
	// 1. Outside resources
	// 2. Basic transactions: Withdraw, Deposit, and Transfer
	// 3. Account retrieval/information endpoints
	// 4. Account creation and updating

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
//________________________________________________________________________________________________________
// 1. Outside resources
	
	@Autowired
	private AccountService accountsDB;
	private Logger logger = Logger.getLogger(AccountController.class.getName());
	
//________________________________________________________________________________________________________
// 2. Basic transactions: Withdraw, Deposit, and Transfer
	
	@PostMapping("/withdraw")
	public ResponseEntity<Object> withdraw(@RequestBody TransDetails deets, HttpSession session) {
		
		logger.info("accounts/withdraw(): Attempting to withdraw $" + deets.getAmount() + " from account ID #" + deets.getTargetID());
		Boolean withdrawal = accountsDB.withdraw(deets.getTargetID(), deets.getAmount());
		
		if (withdrawal) {
			logger.info("accounts/withdraw(): Withdrawal complete");
			return ResponseEntity.ok(new Message("$" + deets.getAmount() + " has been withdrawn from Account #" + deets.getTargetID()));
		}
		
		logger.warning("accounts/withdraw(): Withdrawal failed");
		return ResponseEntity.badRequest().body(new Message("Withdrawal failed"));
		
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<Object> deposit(@RequestBody TransDetails deets, HttpSession session) {
		
		logger.info("accounts/withdraw(): Attempting to deposit $" + deets.getAmount() + " to account ID #" + deets.getTargetID());
		Boolean deposit = accountsDB.deposit(deets.getTargetID(), deets.getAmount());
		
		if (deposit) {
			logger.info("accounts/deposit(): Deposit complete");
			return ResponseEntity.ok(new Message("$" + deets.getAmount() + " has been deposited to Account #" + deets.getTargetID()));
		}
		
		logger.warning("accounts/deposit(): Deposit failed");
		return ResponseEntity.badRequest().body(new Message("Deposit failed"));
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<Object> transfer(@RequestBody TransDetails deets, HttpSession session) {
		
		logger.info("accounts/transfer(): Attempting to transfer " + deets.getAmount() + " from account ID #" 
						+ deets.getSourceID() + " to account ID #" + deets.getTargetID());
		Boolean transfer = accountsDB.transfer(deets.getSourceID(), deets.getTargetID(), deets.getAmount());
		
		if (transfer) {
			logger.info("accounts/transfer(): Transfer complete");
			return ResponseEntity.ok(new Message("$" + deets.getAmount() + " has been transferred from Account #" 
													+ deets.getSourceID() + " to Account #" + deets.getTargetID()));
		}
		
		logger.warning("accounts/transfer(): Transfer failed");
		return ResponseEntity.badRequest().body(new Message("Transfer failed"));
		
	}

//________________________________________________________________________________________________________
// 3. Account retrieval/information endpoints
	
	@GetMapping()
	public ResponseEntity<Object> getAllAccounts(HttpSession session) {
		logger.info("getAllAccounts(): Retrieving all accounts...");
		String role = (String) session.getAttribute("ROLE");
		if (role != null) {
			if (role.equals("Administrator") || role.equals("Employee")) {
				List<Account> rs = accountsDB.getAllAccounts();
				if (rs != null) {
					logger.info("getAllAccounts(): Accounts retrieved");
					return ResponseEntity.ok(rs);
				}
				logger.info("getAllAccounts(): No accounts found");
				return ResponseEntity.accepted().body(new Message("No accounts found"));
			}
			logger.warning("getAllAccounts(): ERROR - Unauthorized access");
			return new ResponseEntity<Object>(new Message("Unauthorized access"), HttpStatus.UNAUTHORIZED);
		}
		
		logger.warning("getAllAccounts(): ERROR - Unauthorized access: Not logged in");
		return new ResponseEntity<Object>(new Message("Unauthorized access: Login with proper credentials"), HttpStatus.UNAUTHORIZED);

	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getAccountById(@PathVariable("id") Integer id, HttpSession session) {
		logger.info("getAccountById(): Retrieving  account Account ID# " + id + "...");
		String role = (String) session.getAttribute("ROLE");
		
		if (role.equals("Administrator") || role.equals("Employee")) {
			Account result = accountsDB.getAccountById(id);
			if (result != null) {
				logger.info("getAccountById(): Account retrieved");
				return ResponseEntity.ok(result);
			}
			logger.info("getAccountById(): Account with ID# " + id + "not found");
			return ResponseEntity.accepted().body(new Message("Account with ID# " + id + " not found"));
		}
		
		logger.warning("getAccountById(): ERROR - Unauthorized access");
		return new ResponseEntity<Object>(new Message("Unauthorized access"), HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("status/{id}")
	public ResponseEntity<Object> getAccountsByStatus(@PathVariable("id") Integer id, HttpSession session) {
		logger.info("getAccountsByStatus(): Retrieving  account Account status ID# " + id + "...");
		String role = (String) session.getAttribute("ROLE");
		
		if (role.equals("Administrator") || role.equals("Employee")) {
			List<Account> rs = accountsDB.getAccountsByStatus(id);
			if (rs != null) {
				logger.info("getAccountsByStatus(): Account retrieved");
				return ResponseEntity.ok(rs);
			}
			logger.info("getAccountsByStatus(): No Accounts with status ID# " + id);
			return ResponseEntity.accepted().body(new Message("No Accounts with status ID# " + id));
		}
		
		logger.warning("getAccountsByStatus(): ERROR - Unauthorized access");
		return new ResponseEntity<Object>(new Message("Unauthorized access"), HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("owner/{id}")
	public ResponseEntity<Object> getAccountsByUId(@PathVariable("id") Integer id, HttpSession session) {
		logger.info("getAccountsByUId(): Retrieving  accounts User ID# " + id + "...");
		Integer sId = (Integer) session.getAttribute("ID");
		String role = (String) session.getAttribute("ROLE");
		
		if (role.equals("Administrator") || role.equals("Employee") || sId.equals(id)) {
			List<Account> rs = accountsDB.getAccountsByUId(id);
			if (rs != null) {
				logger.info("getAccountsByUId(): Accounts retrieved");
				return ResponseEntity.ok(rs);
			}
			logger.info("getAccountsByUId(): No Accounts for User ID# " + id);
			return ResponseEntity.accepted().body(new Message("No Accounts for User ID# " + id));
		}
		
		logger.warning("getAccountById(): ERROR - Unauthorized access");
		return new ResponseEntity<Object>(new Message("Unauthorized access"), HttpStatus.UNAUTHORIZED);
	}

//________________________________________________________________________________________________________
// 4. Account creation and updating

	@PostMapping("{id}/create") 
	public ResponseEntity<Object> createAccount(@PathVariable("id") Integer id, @RequestBody Account acc, HttpSession session) {
		logger.info("createAccount(): Creating new account...");
		Integer sId = (Integer) session.getAttribute("ID");
		String role = (String) session.getAttribute("Role");
		
		if (role.equals("Administrator") || role.equals("Employee") || sId.equals(id)) {
			 Account result = accountsDB.createAccount(id, acc);
			logger.info("createAccount(): Account created");
			 return new ResponseEntity<Object>(result, HttpStatus.CREATED);	
		}
		logger.warning("createAccount(): ERROR - Unauthorized access");
		return new ResponseEntity<Object>(new Message("Unauthorized access"), HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("update")
	public ResponseEntity<Object> updateAccount(@RequestBody Account acc, HttpSession session) {
		logger.info("updateAccount(): Updating account...");
		String role = (String) session.getAttribute("ROLE");
		if (role.equals("Administrator")) {
			Account result = accountsDB.updateAccount(acc);
			if (result != null) {
				logger.info("updateAccount(): Update successful");
				return ResponseEntity.ok(result);
			}
			logger.warning("updateAccount(): ERROR - Update failed");
			return new ResponseEntity<Object>(new Message("Update failed"), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("updateAccount(): ERROR - Unauthorized access");
		return new ResponseEntity<Object>(new Message("Unauthorized access"), HttpStatus.UNAUTHORIZED);
	}

//________________________________________________________________________________________________________
}
