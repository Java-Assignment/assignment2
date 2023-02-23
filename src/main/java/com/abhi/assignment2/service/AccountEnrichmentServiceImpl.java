package com.abhi.assignment2.service;

import com.abhi.assignment2.entity.Account;
import com.abhi.assignment2.entity.AccountEnrichment;
import com.abhi.assignment2.exception.AppAccountNotFoundException;
import com.abhi.assignment2.mapper.AccountMapper;
import com.abhi.assignment2.repository.AccountsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class AccountEnrichmentServiceImpl implements AccountEnrichmentService {
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private AccountMapper accountMapper;

    public AccountEnrichment getByAccountID(String accountID) throws AppAccountNotFoundException {
        Optional<Account> db = accountsRepo.findByAccountID(accountID);
        if (db.isPresent()) {
            Account account = accountsRepo.findByAccountID(accountID).get();
            return accountMapper.convertAcToAccountEnrichment(account);
        }
        else{
            throw new AppAccountNotFoundException("Missing account. AC : "+accountID);
        }

    }
}
