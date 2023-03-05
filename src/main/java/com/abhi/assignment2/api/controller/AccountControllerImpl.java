package com.abhi.assignment2.api.controller;

import com.abhi.assignment2.api.dto.AccountDetailsDTO;
import com.abhi.assignment2.api.entity.Account;
import com.abhi.assignment2.api.exception.AccountFileGenException;
import com.abhi.assignment2.api.exception.AccountFileUploadException;
import com.abhi.assignment2.api.exception.AppAccountNotFoundException;
import com.abhi.assignment2.api.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


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
    public ResponseEntity<AccountDetailsDTO> get(String accountId) throws AppAccountNotFoundException {
        AccountDetailsDTO accountDetailsDTO = accountService.get(accountId);
        return new ResponseEntity<>(accountDetailsDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAccFile() throws AccountFileGenException {
        try {
            String acFile=accountService.createAcFile();
            String[]split=acFile.split("/");
            String fileName=split[split.length-1];

            Path path=Paths.get(URI.create("file://"+acFile));
            InputStream inputStream=Files.newInputStream(path, StandardOpenOption.READ);
            InputStreamResource resource=new InputStreamResource(inputStream);

            HttpHeaders headers=new HttpHeaders();
            headers.add(HttpHeaders.CACHE_CONTROL,"no-cache,no-store,must-revalidate");
            headers.add(HttpHeaders.PRAGMA,"no-cache");
            headers.add(HttpHeaders.EXPIRES,"0");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+fileName+"\"");

            return new ResponseEntity<>(resource,headers,HttpStatus.OK);

        }catch (IOException e) {
            String msg="AccountDetails file creation failed";
            log.error(msg);
            throw new AccountFileGenException(msg);
        }
    }

}

