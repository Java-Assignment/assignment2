package com.abhi.assignment2.controller;

import com.abhi.assignment2.entity.Account;
import com.abhi.assignment2.entity.AccountEnrichment;
import com.abhi.assignment2.exception.AccountFileUploadException;
import com.abhi.assignment2.exception.AppAccountNotFoundException;
import com.abhi.assignment2.service.AccountEnrichmentServiceImpl;
import com.abhi.assignment2.service.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Slf4j
@RestController
public class AccountControllerImpl implements AccountController {

    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private AccountEnrichmentServiceImpl accountEnrichmentServiceImpl;
    @Value("${file.upload-dir}")
    private String fileDirectory;

    @Override
    public ResponseEntity<?> fileUpload(MultipartFile uploadfile) throws AccountFileUploadException, IOException {
        Path path = Paths.get(fileDirectory, uploadfile.getOriginalFilename());
        try (OutputStream outputStream = Files.newOutputStream(path);
             BufferedOutputStream bos = new BufferedOutputStream(outputStream);) {
            bos.write(uploadfile.getBytes());
            bos.flush();
            accountService.addAccounts(path.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("faied to write byte file");
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Account> get(String accountID) throws AppAccountNotFoundException {
        Account account = accountService.get(accountID);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Account>> getCusByAccountName(String customerName) {
        List<Account> acc = accountService.getCusByAccountName(customerName);
        return new ResponseEntity<>(acc, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountEnrichment> getAccountEnrichment(String accountID) throws AppAccountNotFoundException {
        AccountEnrichment accountEnrichment = accountEnrichmentServiceImpl.getByAccountID(accountID);
        return new ResponseEntity<>(accountEnrichment, HttpStatus.OK);
    }


}

