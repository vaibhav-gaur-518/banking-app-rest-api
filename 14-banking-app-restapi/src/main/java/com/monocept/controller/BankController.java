package com.monocept.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.entity.Bank;
import com.monocept.entity.Person;
import com.monocept.service.BankService;

@RestController
@RequestMapping("/banks")
public class BankController {

    @Autowired
    private BankService bankService;
    
    @GetMapping("/{id}")
	public ResponseEntity<Bank> getPersonById(@PathVariable Long id) throws Exception {
		Bank bank = bankService.getBankById(id);
		if (bank == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bank);
	}

    @PostMapping
    public Bank createBank(@Validated @RequestBody Bank bank) {
        return bankService.createBank(bank);
    }
}
