package com.monocept.service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocept.entity.Account;
import com.monocept.entity.Transaction;
import com.monocept.repository.AccountRepository;
import com.monocept.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionService {
    
	@Autowired
    private AccountRepository accountRepository;
    
	@Autowired
    private TransactionRepository transactionRepository;
    
    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    
    public void transactionSave(Account fromAccount, Account toAccount, double amount) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType("Transfer");

        transactionRepository.save(transaction);
    }

	public void transactionSave(Account accountByAccountNumber, Double amount) {
		Transaction transaction = new Transaction();
        transaction.setFromAccount(accountByAccountNumber);
        transaction.setToAccount(accountByAccountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType("Deposit");

        transactionRepository.save(transaction);
	}
    
}
