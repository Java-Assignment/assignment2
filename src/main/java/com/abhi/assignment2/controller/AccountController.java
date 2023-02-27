package com.abhi.assignment2.controller;


import com.abhi.assignment2.entity.Account;
import com.abhi.assignment2.entity.AccountEnrichment;
import com.abhi.assignment2.exception.AccountFileUploadException;
import com.abhi.assignment2.exception.AppAccountNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping(value = "/customeraccounts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@Validated
@Tag(name = "customer accounts api", description = "api operations for customer account")
public interface AccountController {
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "AC file upload")
    ResponseEntity<?> fileUpload(@RequestParam("uploadfile") MultipartFile uploadfile) throws AccountFileUploadException, IOException;


    @GetMapping("/{accountID}")
    @Operation(summary = "Get information about a given account")
    ResponseEntity<Account> get(@PathVariable("accountID") @NotNull @Length(min = 12, max = 12) String accountID) throws AppAccountNotFoundException;

    @GetMapping
    @Operation(summary = "get all accounts by customer name")
    ResponseEntity<List<Account>> getCusByAccountName(@RequestParam(value = "customerName", required = false) String customerName);

    @GetMapping("ID/{accountEnrichmentID}")
    @Operation(summary = "get account details based on account id")
    ResponseEntity<AccountEnrichment> getAccountEnrichment(@PathVariable(value = "accountEnrichmentID") String accountID) throws AppAccountNotFoundException;


}

