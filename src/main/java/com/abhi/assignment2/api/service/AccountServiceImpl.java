package com.abhi.assignment2.api.service;

import com.abhi.assignment2.api.dto.AccountDTO;
import com.abhi.assignment2.api.entity.Account;
import com.abhi.assignment2.api.exception.AppAccountNotFoundException;
import com.abhi.assignment2.api.repository.AccountsRepo;
import com.abhi.assignment2.externalsvc.AccountRefDataService;
import com.abhi.assignment2.externalsvc.CustomerRefDataService;
import com.abhi.assignment2.mapper.AccountDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private AccountRefDataService accountRefDataService;
    @Autowired
    private CustomerRefDataService customerRefDataService;
    @Autowired
    private AccountDTOMapper accountDTOMapper;

    @Override
    public void saveFileDataToDB(Path path) {
//        accountsRepo.deleteAll();
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");
                String dateString = split[3];
                Account account = new Account(split[0], split[1], Float.parseFloat(split[2]), LocalDate.from(formatter.parse(dateString)));
                accountsRepo.save(account);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account get(String accountID) throws AppAccountNotFoundException {
        Optional<Account> accountOptional = accountsRepo.findByAccountID(accountID);
        if (accountOptional.isPresent()) {
            // TODO - add account and customer infoermation here. by calling reference data serivces ( as3 and as4 APIs )
//         hint - WebClient
            Account acc=accountOptional.get();
            String cusname = accountOptional.get().getCustomerName();
            AccountDTO accountRefDataService1 = accountRefDataService.getAccountById(accountID);
            AccountDTO customerRefDataService1 = customerRefDataService.getCustomerByName(cusname);
            log.info(String.valueOf(accountRefDataService1));
            System.out.println(accountRefDataService1);
            System.out.println(customerRefDataService1);
            return acc;
        } else {
        throw new AppAccountNotFoundException("Missing account. AC : " + accountID);
    }
    }
}
