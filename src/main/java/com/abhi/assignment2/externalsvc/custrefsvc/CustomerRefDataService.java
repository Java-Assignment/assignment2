package com.abhi.assignment2.externalsvc.custrefsvc;

import com.abhi.assignment2.externalsvc.custrefsvc.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CustomerRefDataService {

    @Autowired
    private WebClient webClient;

    @Value("${customer-ref-svc-url}")
    private String customerRefSvcUrl;


    public CustomerRefDataService() {
        webClient = WebClient.builder()
                .baseUrl(customerRefSvcUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public CustomerDTO getCustomerByName(String customerName) {
        return webClient.get()
                .uri( customerRefSvcUrl+ customerName)
                .retrieve()
                .bodyToMono(CustomerDTO.class).block();
    }
}
