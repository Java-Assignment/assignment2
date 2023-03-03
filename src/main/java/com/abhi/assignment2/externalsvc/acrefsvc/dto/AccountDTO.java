package com.abhi.assignment2.externalsvc.acrefsvc.dto;

import com.abhi.assignment2.common.vo.AccountStatus;
import com.abhi.assignment2.common.vo.AccountType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor

public class AccountDTO {
    private String accountId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createDate;

    private AccountType accountType;
    private AccountStatus accountStatus;

}
