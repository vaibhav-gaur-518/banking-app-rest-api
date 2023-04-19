package com.monocept.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.entity.Bank;
import com.monocept.entity.Person;
import com.monocept.repository.BankRepository;
import com.monocept.service.BankService;
import com.monocept.service.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	BankRepository bankRepository;

	@GetMapping("/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
		Person person = personService.getPersonById(id);		
		if (person == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(person);
	}

	@PostMapping("/{bankId}")
	public ResponseEntity<Person> createPerson(@RequestBody Person person,@PathVariable Long bankId) throws Exception {
//		Person createdPerson = personService.createPerson(person);
//		return ResponseEntity.created(URI.create("/persons/" + createdPerson.getId())).body(createdPerson);
		Bank bank = bankService.getBankById(bankId);
	    if (bank == null) {
	        return ResponseEntity.notFound().build();
	    }
	    person.setBank(bank);
	    Person createdPerson = personService.createPerson(person);
	    bank.addPerson(createdPerson);
	    return ResponseEntity.created(URI.create("/persons/" + createdPerson.getId())).body(createdPerson);
	}

	@GetMapping("/{personId}/total_balance")
	public ResponseEntity<Double> getTotalBalance(@PathVariable Long personId) {
		Double totalBalance = personService.getTotalBalance(personId);
		if (totalBalance == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(totalBalance);
	}
}