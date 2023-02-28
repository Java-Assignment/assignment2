package com.abhi.assignment2.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDTO {

    private String accountID;
    private String customerName;
    private Float accountBalance;
    private LocalDate createDate;
}
