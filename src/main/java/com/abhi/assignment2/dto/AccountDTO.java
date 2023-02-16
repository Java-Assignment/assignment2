package com.abhi.assignment2.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
public class AccountDTO {
    private String accountID;
    private String customerName;
    private Float accountBalance;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createDate;

}
