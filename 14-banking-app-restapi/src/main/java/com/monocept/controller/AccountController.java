package com.monocept.controller;

import java.net.URI;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.entity.Account;
import com.monocept.service.AccountService;
import com.monocept.service.TransactionService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber) {
		Account account = accountService.getAccountByAccountNumber(accountNumber);
		if (account == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(account);
	}

	@PostMapping("/{personId}")
	public ResponseEntity<Account> createAccount(@RequestBody Account account, @PathVariable Long personId) {
		Account createdAccount = accountService.createAccount(account, personId);
		return ResponseEntity.created(URI.create("/accounts/" + createdAccount.getAccountNumber()))
				.body(createdAccount);
	}

	@GetMapping("/person/{personId}")
	public ResponseEntity<Set<Account>> getAccountsByPersonId(@PathVariable Long personId) {
		Set<Account> accounts = accountService.getAccountsByPersonId(personId);
		if (accounts.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(accounts);
	}

	@PutMapping("/{fromAccountNumber}/{toAccountNumber}/{amount}")
	public ResponseEntity<String> transferMoney(@PathVariable String fromAccountNumber,
			@PathVariable String toAccountNumber, @PathVariable Double amount) throws Exception {
		try {
			accountService.transferMoney(fromAccountNumber, toAccountNumber, amount);
			transactionService.transactionSave(accountService.getAccountByAccountNumber(fromAccountNumber),
					accountService.getAccountByAccountNumber(toAccountNumber), amount);
			return ResponseEntity.ok("Transfer successful");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Insufficient balance");
		}
	}

	@PutMapping("/self_transfer/{accountNumber}/{amount}")
	public ResponseEntity<String> selfTransferMoney(@PathVariable String accountNumber, @PathVariable Double amount)
			throws Exception {
		try {
			accountService.selfTransferMoney(accountNumber, amount);
			transactionService.transactionSave(accountService.getAccountByAccountNumber(accountNumber), amount);
			return ResponseEntity.ok("Transfer successful");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Insufficient balance");
		}
	}
}