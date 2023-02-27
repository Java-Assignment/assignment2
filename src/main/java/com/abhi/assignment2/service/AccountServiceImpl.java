package com.abhi.assignment2.service;

import com.abhi.assignment2.entity.Account;
import com.abhi.assignment2.entity.AccountStatus;
import com.abhi.assignment2.entity.AccountType;
import com.abhi.assignment2.exception.AppAccountNotFoundException;
import com.abhi.assignment2.repository.AccountsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountsRepo accountsRepo;
    @Value("${file.upload-dir}")
    private String filedirectory;

    private static Account savedaccount;

    private static Path path;

    public AccountServiceImpl(AccountsRepo accountsRepo) {
        this.accountsRepo = accountsRepo;
    }


    @Override
    public Account addAccounts(String upload) {

        path = Paths.get(upload);
        log.info(String.valueOf(path));

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
//                String line="Veyi Kaga,000000000000,1430.0,2023-02-18";
                String[] split = line.split(",");
                Account account = new Account();
                account.setCustomerName(split[1]);
                account.setAccountID(split[0]);
                account.setAccountBalance(Float.valueOf(split[2]));
                account.setCreateDate(LocalDate.parse(split[3]));
                account.setAccountType(AccountType.generateRandomAccountType());
                account.setAccountStatus(AccountStatus.generateRandomAccountStatus());
                savedaccount = accountsRepo.save(account);
                System.out.println(savedaccount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("file read failed");

        }
        return savedaccount;
    }


    @Override
    public Account get(String accountID) throws AppAccountNotFoundException {
        Optional<Account> dbac = accountsRepo.findByAccountID(accountID);
        if (dbac.isPresent()) {
            Account account = accountsRepo.findByAccountID(accountID).get();
            log.info("account :" + account);
            return account;
        } else {
            throw new AppAccountNotFoundException("Missing account id." + accountID);
        }
    }

    @Override
    public List<Account> getCusByAccountName(String customerName) {
        List<Account> accounts;
        if (customerName != null) {
            accounts = accountsRepo.findByCustomerName(customerName).stream()
                    .collect(Collectors.toList());
            ;
            log.info("In if else clause", accounts);
        } else {
            accounts = accountsRepo.findAll().stream().collect(Collectors.toList());
        }
        log.info("accounts.size():" + accounts.size());
        return accounts;
    }

}

