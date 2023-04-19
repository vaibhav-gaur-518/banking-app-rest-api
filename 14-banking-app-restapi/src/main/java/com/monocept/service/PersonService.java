package com.monocept.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocept.entity.Account;
import com.monocept.entity.Person;
import com.monocept.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PersonService {
    
    @Autowired
    private PersonRepository personRepository;
    
    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }
    
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }
    
    public Double getTotalBalance(Long personId) {
        Person person = getPersonById(personId);
        
        Double totalBalance = 0.0;
        for(Account account : person.getAccounts()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }
}
