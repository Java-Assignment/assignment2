package com.abhi.assignment2.externalsvc.custrefsvc;

import com.abhi.assignment2.externalsvc.acrefsvc.dto.AccountDTO;
import com.abhi.assignment2.externalsvc.custrefsvc.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class CustomerRefDataService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${customer-ref-svc-url}")
    private String customerRefSvcUrl;
    private URI uri;


    public CustomerRefDataService() {
        uri = UriComponentsBuilder.fromHttpUrl("http://localhost:9004/customer/").build().toUri();
    }

    public CustomerDTO getCustomerByName(String customerName) {
        WebClient webClient = webClientBuilder.build();
        CustomerDTO customerDetailsDTO = webClient.get()
                .uri(uri + customerName)
                .exchangeToMono(
                        response -> {
                            if (response.statusCode().is2xxSuccessful()) {
                                return response.bodyToMono(CustomerDTO.class);
                            } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                                return Mono.empty();
                            } else {
                                return response.createException().flatMap(Mono::error);
                            }
                        }
                )
                .block();
        log.info("AccountRefDataService getAccountById:" + customerDetailsDTO);
        return customerDetailsDTO;
    }
}
