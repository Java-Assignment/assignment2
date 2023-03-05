package com.abhi.assignment2.api.entity;

import com.abhi.assignment2.api.dto.AccountDetailsDTO;
import com.abhi.assignment2.externalsvc.acrefsvc.dto.AccountDTO;
import com.abhi.assignment2.externalsvc.custrefsvc.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(collection = "as5allaccountsdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {

    @Id
    private String accountID;
    private String customerName;
    private Float accountBalance;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate createDate;
    private AccountDTO accountDTO;
    private CustomerDTO customerDTO;
}
