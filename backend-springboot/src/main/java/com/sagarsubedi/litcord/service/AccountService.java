package com.sagarsubedi.litcord.service;

import com.sagarsubedi.litcord.Exceptions.AccountCreationConflictException;
import com.sagarsubedi.litcord.dao.AccountRepository;
import com.sagarsubedi.litcord.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account createAccount(String name, String email){
        Optional<Account> account = accountRepository.findAccountByEmail(email);
        if(account.isPresent()){
            throw  new AccountCreationConflictException("The email is already used by another account");
        }
        return accountRepository.save(new Account(name, email));
    }

    public  Account getAccountById(Long id){
        return accountRepository.findById(id).orElseThrow();
    }
}
