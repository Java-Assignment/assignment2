package com.abhi.assignment2.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AccountDTO {

    private String accountID;
    private String customerName;
    private Float accountBalance;
    private LocalDate createDate;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private int age;
    private String relationship;
    private String address;
}

//@NoArgsConstructor
//public class CustomerDTO {
//    private String customerName;
//    private int age;
//    private String relationship;
//    private String address;
//}
//public class AccountDTO {
//    private String accountId;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    private LocalDate createDate;
//
//    private AccountType accountType;
//    private AccountStatus accountStatus;
//
//}