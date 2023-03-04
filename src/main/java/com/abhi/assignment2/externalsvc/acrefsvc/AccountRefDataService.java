package com.abhi.assignment2.externalsvc.acrefsvc;

import com.abhi.assignment2.externalsvc.acrefsvc.dto.AccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Slf4j
public class AccountRefDataService {
    @Autowired
    private WebClient webClient;

    private URI uri;

    public AccountRefDataService() {
        uri = UriComponentsBuilder.fromHttpUrl("http://localhost:9003/accounts/").build().toUri();
    }

    public AccountDTO getAccountById(String accountID) {
        AccountDTO accountDetailsDTO = webClient.get()
                .uri(uri + accountID)
                .exchangeToMono(
                        response -> {
                            if (response.statusCode().is2xxSuccessful()) {
                                return response.bodyToMono(AccountDTO.class);
                            } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                                return Mono.empty();
                            } else {
                                return response.createException().flatMap(Mono::error);
                            }
                        }
                )
                .block();
        log.info("AccountRefDataService getAccountById:" + accountDetailsDTO);
        return accountDetailsDTO;

    }

}
