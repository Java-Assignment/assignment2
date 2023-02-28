package com.abhi.assignment2.api.service;

import com.abhi.assignment2.api.entity.Account;
import com.abhi.assignment2.api.repository.AccountsRepo;
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
public class AccountServiceImpl implements AccountService{
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private AccountsRepo accountsRepo;

    @Override
    public void saveFileDataToDB(Path path) {
        accountsRepo.deleteAll();
        try (BufferedReader bufferedReader=Files.newBufferedReader(path)){
            String line;
            while ( (line=bufferedReader.readLine())  != null ){
                String[] split = line.split(",");
                String dateString =split[3];
                Account account = new Account(split[0], split[1], Float.parseFloat(split[2]), LocalDate.from(formatter.parse(dateString)) );
                accountsRepo.save(account);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account get(String accountID) {
        Optional<Account> accountOptional= accountsRepo.findByAccountID(accountID);
        // TODO - add account and customer infoermation here. by calling reference data serivces ( as3 and as4 APIs )
        // hint - WebClient

        if ( accountOptional.isPresent()){
            return accountOptional.get();
        }
        return null;
    }
}
