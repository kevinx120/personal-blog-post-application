package com.example.blogproject.services;


import com.example.blogproject.models.Account;
import com.example.blogproject.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository; //to interface with the database

    public Account save(Account account) {  //where we pass in an new account it will try to persist it to the database
        account.setPassword(passwordEncoder.encode(account.getPassword())); //whatever we use as the password where gonna encode with our password encoder that we declared in the bean web security config object which then injected into the account service through the autowired declaration
        return accountRepository.save(account);
    }

    public Optional<Account> findByEmail(String email){
        return accountRepository.findOneByEmail(email);
    }
}
