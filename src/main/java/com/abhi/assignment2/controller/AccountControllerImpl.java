package com.abhi.assignment2.controller;
import com.abhi.assignment2.dto.AccountDTO;
import com.abhi.assignment2.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.abhi.assignment2.exception.AccountFileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Slf4j
@RestController
public class AccountControllerImpl implements AccountController {

    @Autowired
    private FileService fileService;
    @Value("${file.upload-dir}")
    private String filedirectory;

    @Override
    public ResponseEntity<?> fileUpload(MultipartFile uploadfile) throws AccountFileUploadException, IOException {
        Path path = Paths.get(filedirectory, uploadfile.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(path.toFile());
        byte[] strToBytes = uploadfile.getBytes();
        outputStream.write(strToBytes);

        outputStream.close();


        return new ResponseEntity<>(fileService.addFile(uploadfile), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> getAccounts(String id) throws FileNotFoundException {
        String accounts = fileService.getAccounts(id);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}

