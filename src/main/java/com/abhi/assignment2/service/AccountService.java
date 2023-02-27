package com.abhi.assignment2.service;

import com.abhi.assignment2.entity.Account;
import com.abhi.assignment2.exception.AppAccountNotFoundException;

import java.util.List;

public interface AccountService {
    Account addAccounts(String upload);

    Account get(String accountID) throws AppAccountNotFoundException;

    List<Account> getCusByAccountName(String customerName);
}
