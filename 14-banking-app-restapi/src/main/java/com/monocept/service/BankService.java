package com.monocept.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monocept.entity.Bank;
import com.monocept.repository.BankRepository;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public Bank getBankById(Long id) throws Exception {
        return bankRepository.findById(id)
                .orElseThrow(() -> new Exception("Bank id not found"));
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }
}
