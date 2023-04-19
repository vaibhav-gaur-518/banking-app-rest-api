package com.monocept.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocept.entity.Account;
import com.monocept.entity.Person;
import com.monocept.repository.AccountRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PersonService personService;
    
    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
    
    public Account createAccount(Account account, Long personId) {
        Person person = personService.getPersonById(personId);
        
        account.setPerson(person);
        return accountRepository.save(account);
    }
    
    public Set<Account> getAccountsByPersonId(Long personId) {
        Person person = personService.getPersonById(personId);
        
        return person.getAccounts();
    }
    
    public void transferMoney(String fromAccountNumber, String toAccountNumber, Double amount) throws Exception {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        if(fromAccount == null) {
            throw new Exception("Account not found with account number " + fromAccountNumber);
        }
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);
        if(toAccount == null) {
            throw new Exception("Account not found with account number " + toAccountNumber);
        }
        fromAccount.transfer(toAccount, amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

	public void selfTransferMoney(String accountNumber, Double amount) throws Exception {
		Account self = accountRepository.findByAccountNumber(accountNumber);
		if(self == null) {
            throw new Exception("Account not found with account number " + accountNumber);
        }
		self.transfer(amount);
		accountRepository.save(self);
	}
}