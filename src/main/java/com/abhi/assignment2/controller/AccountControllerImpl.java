package com.abhi.assignment2.controller;
import org.springframework.beans.factory.annotation.Value;
import com.abhi.assignment2.exception.AccountFileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

@Slf4j
@RestController
public class AccountControllerImpl implements AccountController {
    @Value("${file.upload-dir}")
    private String filedirectory;
    @Override
    public ResponseEntity<String> fileUpload(MultipartFile uploadfile) throws AccountFileUploadException, IOException {
        Path path = Paths.get(filedirectory, uploadfile.getOriginalFilename());
        File newFile = new File(path.toString());
        Files.write(path, uploadfile.getBytes());
//        Path path=Path.of(filedirectory);
////        Files.createFile(path);
//        Files.write(path,uploadfile.getBytes());
        return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);

//        log.info(uploadfile.getOriginalFilename());
//        try {
//            InputStream inputStream = uploadfile.getInputStream();
//            byte[] bytes = inputStream.readAllBytes();
//            String fileContent = new String(bytes);
//            System.out.println(fileContent);
//            return new ResponseEntity<>("file upload Successful", HttpStatus.OK);
//
//        } catch (IOException e) {
//            String msg="Account file upload failed";
//            log.error(msg);
//            throw new AccountFileUploadException(msg);
//
//        }
    }
}


