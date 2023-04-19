package com.monocept.entity;

import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String accountNumber;
    
    @Column(nullable = false)
    private Double balance;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    
    @OneToMany(mappedBy = "fromAccount")
    private Set<Transaction> outgoingTransactions;
    
    @OneToMany(mappedBy = "toAccount")
    private Set<Transaction> incomingTransactions;
    
    public Account() {
        this.balance = 1000.0;
    }

	public Account(Long id, String accountNumber, Double balance, Person person, Set<Transaction> outgoingTransactions,
			Set<Transaction> incomingTransactions) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.person = person;
		this.outgoingTransactions = outgoingTransactions;
		this.incomingTransactions = incomingTransactions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Set<Transaction> getOutgoingTransactions() {
		return outgoingTransactions;
	}

	public void setOutgoingTransactions(Set<Transaction> outgoingTransactions) {
		this.outgoingTransactions = outgoingTransactions;
	}

	public Set<Transaction> getIncomingTransactions() {
		return incomingTransactions;
	}

	public void setIncomingTransactions(Set<Transaction> incomingTransactions) {
		this.incomingTransactions = incomingTransactions;
	}

	public void transfer(Account toAccount, Double amount) throws Exception {
	    if(this.balance < amount) {
	        throw new Exception("Insufficient balance in account " + this.accountNumber);
	    }
	    this.balance -= amount;
	    toAccount.balance += amount;;
    }

	public void transfer(Double amount) throws Exception {
		if(this.balance < amount) {
	        throw new Exception("Insufficient balance in account " + this.accountNumber);
	    }
		this.balance += amount;
	}

	public void deposit(Double amount) throws Exception {
		if(this.balance < amount) {
	        throw new Exception("Insufficient balance in account " + this.accountNumber);
	    }
		this.balance += amount;
	}

	public void withdraw(Double amount) throws Exception {
		if(this.balance < amount) {
	        throw new Exception("Insufficient balance in account " + this.accountNumber);
	    }
		this.balance -= amount;
	}
}
