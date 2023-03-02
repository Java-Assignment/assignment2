package com.abhi.assignment2.api.service;

import com.abhi.assignment2.api.dto.AccountDTO;
import com.abhi.assignment2.api.entity.Account;
import com.abhi.assignment2.api.repository.AccountsRepo;
import com.abhi.assignment2.externalsvc.AccountRefDataService;
import com.abhi.assignment2.externalsvc.CustomerRefDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private AccountRefDataService accountRefDataService;
    @Autowired
    private CustomerRefDataService customerRefDataService;


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
    public Account get(String accountID) {
        Optional<Account> accountOptional = accountsRepo.findByAccountID(accountID);
        if (accountOptional.isPresent()) {
            // TODO - add account and customer infoermation here. by calling reference data serivces ( as3 and as4 APIs )
//         hint - WebClient
            String cusname = accountOptional.get().getCustomerName();
            Mono<AccountDTO> accountRefDataService1 = accountRefDataService.getAccountById(accountID);
            Mono<AccountDTO> customerRefDataService1 = customerRefDataService.getCustomerByName(cusname);
            System.out.println(accountRefDataService1);
            System.out.println(customerRefDataService1);
            return accountOptional.get();
        }
        return null;
    }
}
