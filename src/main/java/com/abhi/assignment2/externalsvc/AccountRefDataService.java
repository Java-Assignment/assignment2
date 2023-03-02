package com.abhi.assignment2.externalsvc;

import com.abhi.assignment2.api.dto.AccountDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Slf4j
public class AccountRefDataService {
    @Autowired
    WebClient webClient;

    private URI uri;

    @PostConstruct
    public void init() {
        // webClient= WebClient.builder().baseUrl("http://localhost:9003/accounts").defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
         uri = UriComponentsBuilder.fromHttpUrl("http://localhost:9003/accounts/").build().toUri();

    }

    @GetMapping("/accounts/{accountID}")
    public AccountDTO getAccountById(@PathVariable String accountID) {
        AccountDTO accountDTO = webClient.get()
                .uri(uri+accountID)
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
        log.info("AccountSvcWebClient GET ac:"+accountDTO);
        return accountDTO;

        //return webClient.get().uri(uri + accountID).retrieve().bodyToMono(AccountDTO.class).contentType(MediaType.MULTIPART_FORM_DATA).accept(MediaType.APPLICATION_JSON).block();
    }

}
