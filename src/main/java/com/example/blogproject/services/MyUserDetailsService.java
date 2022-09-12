package com.example.blogproject.services;


import com.example.blogproject.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")    //its a kinda special security helper text
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    //this loadUserByUsername is what spring security framework will use within that login processing chain of events its g
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        if (!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("Account not found");
        }

        Account account = optionalAccount.get();
        //where taking the authorities of the account and then mapping it to the collections that wil pass into the user deatils object
        List<GrantedAuthority> grantedAuthorities = account
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
//when we succefuly loin it should send us to the home page
        return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(), grantedAuthorities);
        // this last return has much functionality
        //i think also for using email and password for the account
    }
}
