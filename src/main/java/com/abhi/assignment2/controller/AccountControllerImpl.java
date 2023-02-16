package com.abhi.assignment2.controller;

import com.abhi.assignment2.exception.AccountFileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@Slf4j
public class AccountControllerImpl implements AccountController {
    @Override
    public ResponseEntity<String> fileUpload(MultipartFile uploadfile) throws AccountFileUploadException {
        log.info(uploadfile.getOriginalFilename());
        try {
            InputStream inputStream = uploadfile.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            String fileContent = new String(bytes);
            System.out.println(fileContent);
            return new ResponseEntity<>("file upload Successful", HttpStatus.OK);

        } catch (IOException e) {
            String msg="Account file upload failed";
            log.error(msg);
            throw new AccountFileUploadException(msg);

        }
    }
}


