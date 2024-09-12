package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.*;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class AccountService {    

    private final AccountRepository accountRepository;    
    
    public AccountService(AccountRepository accountRepository){                
        this.accountRepository = accountRepository;       
    }

    public Account addAccount(Account account){
        String username = account.getUsername();
        Optional<Account> optAccount = accountRepository.findByUsername(username);
        if(optAccount.isPresent() || username.equals(""))
            return null;
        else
            return accountRepository.save(account);
    }

    public Account loginAccount(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        Optional<Account> optAccount = accountRepository.findByUsername(username);
        if(optAccount.isPresent()){
            Account confAccount = optAccount.get();
            if(password.equals(confAccount.getPassword()))
                return confAccount;
            else
                return null;            
        }        
        else
            return null;
    }
}
