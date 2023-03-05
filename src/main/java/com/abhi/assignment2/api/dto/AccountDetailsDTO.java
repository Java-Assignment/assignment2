package com.abhi.assignment2.api.dto;

import com.abhi.assignment2.externalsvc.acrefsvc.dto.AccountDTO;
import com.abhi.assignment2.externalsvc.custrefsvc.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AccountDetailsDTO {

    private String accountID;
    private String customerName;
    private Float accountBalance;
    private LocalDate createDate;
    private AccountDTO accountDTO;
    private CustomerDTO customerDTO;
}
