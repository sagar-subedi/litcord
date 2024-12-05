package com.sagarsubedi.litcord.service.auth;

import com.sagarsubedi.litcord.Exceptions.AccountCreationConflictException;
import com.sagarsubedi.litcord.config.JwtService;
import com.sagarsubedi.litcord.dao.AccountRepository;
import com.sagarsubedi.litcord.dto.LoginDTO;
import com.sagarsubedi.litcord.dto.AccountDTO;
import com.sagarsubedi.litcord.dto.response.LoginResponse;
import com.sagarsubedi.litcord.model.Account;
import com.sagarsubedi.litcord.serviceimpl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<LoginResponse>    login(LoginDTO loginDTO) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwtToken = jwtService.generateJwtToken(authentication);

        return ResponseEntity.ok(LoginResponse.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .token(jwtToken)
                .build());
    }

    public Account register(AccountDTO accountDTO) {
        Optional<Account>  account = accountRepository.findAccountByEmail(accountDTO.getEmail());
        if (account.isPresent()) {
            throw new AccountCreationConflictException("The email is already used by another account");
        }
        return accountRepository.save(new Account(accountDTO.getName(), accountDTO.getEmail(), passwordEncoder.encode(accountDTO.getPassword())));
    }
}
