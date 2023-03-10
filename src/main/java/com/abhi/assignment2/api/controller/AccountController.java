package com.abhi.assignment2.api.controller;


import com.abhi.assignment2.api.dto.AccountDetailsDTO;
import com.abhi.assignment2.api.entity.Account;
import com.abhi.assignment2.api.exception.AccountFileGenException;
import com.abhi.assignment2.api.exception.AccountFileUploadException;
import com.abhi.assignment2.api.exception.AppAccountNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping(value = "/accounts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Validated
@Tag(name = "customer accounts api", description = "api operations for customer account")
public interface AccountController {

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "AC file upload")
    ResponseEntity<?> fileUpload(@RequestParam("uploadfile") MultipartFile uploadfile) throws AccountFileUploadException, IOException;


    @GetMapping("/{accountId}")
    @Operation(summary = "Get information about a given account")
    ResponseEntity<AccountDetailsDTO> get(@PathVariable("accountId") @NotNull @Length(min = 12, max = 12) String accountId) throws AppAccountNotFoundException;

    @GetMapping("/file")
    @Operation(summary = "Creation of report")
    ResponseEntity<Object>getAccFile() throws AccountFileGenException;

}

