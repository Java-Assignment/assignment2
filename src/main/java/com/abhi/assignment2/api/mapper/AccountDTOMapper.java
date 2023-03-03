package com.abhi.assignment2.api.mapper;

import com.abhi.assignment2.api.dto.AccountDetailsDTO;
import com.abhi.assignment2.api.entity.Account;
import org.mapstruct.Mapper;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface AccountDTOMapper {
    AccountDetailsDTO convertAccountToAccountDetailsDTO(Account account);
}
