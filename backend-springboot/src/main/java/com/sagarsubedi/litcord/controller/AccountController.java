package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.Exceptions.ServerCreationConflictException;
import com.sagarsubedi.litcord.dto.AccountDTO;
import com.sagarsubedi.litcord.model.Account;
import com.sagarsubedi.litcord.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Handles user creation, deletion, update
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountDTO account){
        try{
             Account createdAccount = accountService.createAccount(account.getName(), account.getEmail());
            return new ResponseEntity<>(createdAccount.getId().toString(), HttpStatus.CREATED);
        }catch(ServerCreationConflictException e){
            return new ResponseEntity<>("Account already present", HttpStatus.CONFLICT);
        }catch(Exception e){
            return new ResponseEntity<>("Something happened. Account not created.", HttpStatus.CONFLICT);
        }
    }
}