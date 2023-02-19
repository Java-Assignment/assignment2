package com.abhi.assignment2.controller;


import com.abhi.assignment2.dto.AccountDTO;
import com.abhi.assignment2.exception.AccountFileUploadException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RequestMapping(value = "/customeraccounts",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
@Validated
@Tag(name = "customer accounts api",description = "api operations for customer account")
public interface AccountController {
    @PostMapping(value = "/file",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "AC file upload")
    ResponseEntity<?> fileUpload(@RequestParam("uploadfile") MultipartFile uploadfile) throws AccountFileUploadException, IOException;


    @GetMapping("/{id}")
    @Operation(summary ="get all saved details of file" )
    ResponseEntity<String> getAccounts(@RequestParam(value="id") String id) throws FileNotFoundException;
}

