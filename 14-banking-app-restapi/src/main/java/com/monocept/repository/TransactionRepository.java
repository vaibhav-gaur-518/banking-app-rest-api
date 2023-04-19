package com.monocept.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}