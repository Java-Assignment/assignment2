package com.abhi.assignment2.mapper;

import com.abhi.assignment2.entity.Account;
import com.abhi.assignment2.entity.AccountEnrichment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEnrichment convertAcToAccountEnrichment(Account account);
}
