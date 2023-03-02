package com.abhi.assignment2.api.controller;

import com.abhi.assignment2.api.entity.Account;
import com.abhi.assignment2.api.exception.AccountFileUploadException;
import com.abhi.assignment2.api.exception.AppAccountNotFoundException;
import com.abhi.assignment2.api.service.AccountService;
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


@Slf4j
@RestController
public class AccountControllerImpl implements AccountController {

    @Autowired
    private AccountService accountService;

    @Value("${file.upload-dir}")
    private String fileDirectory;

    @Override
    public ResponseEntity<?> fileUpload(MultipartFile uploadfile) throws AccountFileUploadException, IOException {
        System.out.println("executing upload file . "+uploadfile.getOriginalFilename());
        System.out.println("save file to "+fileDirectory);
        Path path = Paths.get(fileDirectory, uploadfile.getOriginalFilename());
        if ( Files.exists(path)){
            Files.delete(path);
        }
        try (OutputStream outputStream = Files.newOutputStream(path);
             BufferedOutputStream bos = new BufferedOutputStream(outputStream);) {
            log.info("before bos write",bos);
            bos.write(uploadfile.getBytes());
            log.info("after bos write",bos);

            bos.flush();
            System.out.println("File written");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed to write file");
        }
        accountService.saveFileDataToDB(path);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Account> get(String accountID) throws AppAccountNotFoundException {
        Account account = accountService.get(accountID);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


}

