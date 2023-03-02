package com.abhi.assignment2.externalsvc;

import com.abhi.assignment2.api.dto.AccountDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class CustomerRefDataService {
    @Autowired
    WebClient webClient;

    @PostConstruct
    public void init(){
        webClient= WebClient.builder().baseUrl("http://localhost:9004/customer").defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    @GetMapping("/customer/{customerName}")
    public Mono<AccountDTO> getCustomerByName(@PathVariable String customerName ){
        return webClient.get().uri("/customer/"+customerName).retrieve().bodyToMono(AccountDTO.class);
    }
}
