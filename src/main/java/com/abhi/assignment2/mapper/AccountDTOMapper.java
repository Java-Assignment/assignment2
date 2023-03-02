package com.abhi.assignment2.mapper;

import com.abhi.assignment2.api.dto.AccountDTO;
import org.mapstruct.Mapper;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface AccountDTOMapper {
    AccountDTO convertMonoAccountToAccountDTO(Mono<AccountDTO> accountRefDataService1);
}
