package com.sagarsubedi.litcord.serviceimpl;

import com.sagarsubedi.litcord.dao.AccountRepository;
import com.sagarsubedi.litcord.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    //Maybe use constructor injection instead of filed
    //As of this implementation, UserDetailsServiceImpl needs to be passed profileRepository for instantiation
    //Is @Autowired and constructor injection both necessary
    @Autowired
    private final AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account user = accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Error: User not found for the email: " + email));

        return UserDetailsImpl.build(user);
    }
}
