package com.app.bankingAPI_Spring.controllers;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bankingAPI_Spring.models.Message;
import com.app.bankingAPI_Spring.models.TransDetails;
import com.app.bankingAPI_Spring.services.AccountService;

// Controller for Account related API calls
	// 1. Outside resources
	// 2. Basic transactions: Withdraw, Deposit, and Transfer

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


}
