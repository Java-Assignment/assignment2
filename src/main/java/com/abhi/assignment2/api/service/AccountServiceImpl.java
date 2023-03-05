package com.abhi.assignment2.api.service;

import com.abhi.assignment2.api.dto.AccountDetailsDTO;
import com.abhi.assignment2.api.entity.Account;
import com.abhi.assignment2.api.entity.AccountDetails;
import com.abhi.assignment2.api.exception.AppAccountNotFoundException;
import com.abhi.assignment2.api.mapper.AccountDTOMapper;
import com.abhi.assignment2.api.repository.AccountDetailsRepo;
import com.abhi.assignment2.api.repository.AccountsRepo;
import com.abhi.assignment2.externalsvc.acrefsvc.AccountRefDataService;
import com.abhi.assignment2.externalsvc.acrefsvc.dto.AccountDTO;
import com.abhi.assignment2.externalsvc.custrefsvc.CustomerRefDataService;
import com.abhi.assignment2.externalsvc.custrefsvc.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    private AccountDetailsRepo accountDetailsRepo;

    @Autowired
    private AccountsRepo accountsRepo;
    @Value("${file-report-url}")
    private String file_report_url;
    @Autowired
    private AccountRefDataService accountRefDataService;
    @Autowired
    private CustomerRefDataService customerRefDataService;
    @Autowired
    private AccountDTOMapper accountDTOMapper;

    @Override
    public void saveFileDataToDB(Path path) {
        accountsRepo.deleteAll();
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");
                String dateString = split[3];
                Account account = new Account(split[0], split[1], Float.parseFloat(split[2]), LocalDate.from(formatter.parse(dateString)));
                accountsRepo.save(account);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountDetailsDTO get(String accountID) throws AppAccountNotFoundException {
        Optional<Account> accountOptional = accountsRepo.findByAccountID(accountID);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            AccountDetailsDTO accountDetailsDTO = accountDTOMapper.convertAccountToAccountDetailsDTO(account);
            String cusname = accountOptional.get().getCustomerName();
            AccountDTO accountDTO = accountRefDataService.getAccountById(accountID);
            CustomerDTO customerDTO = customerRefDataService.getCustomerByName(cusname);

            System.out.println(accountDTO);
            System.out.println(customerDTO);
            // add data from ref services to main DTO object
            accountDetailsDTO.setAccountDTO(accountDTO);
            accountDetailsDTO.setCustomerDTO(customerDTO);
            AccountDetails accountDetails=new AccountDetails(accountDetailsDTO.getAccountID(),accountDetailsDTO.getCustomerName(),accountDetailsDTO.getAccountBalance(),accountDetailsDTO.getCreateDate(),accountDetailsDTO.getAccountDTO(),accountDetailsDTO.getCustomerDTO());
            accountDetailsRepo.save(accountDetails);
            return accountDetailsDTO;
        } else {
            throw new AppAccountNotFoundException("Missing account. AC : " + accountID);
        }
    }

    @Override
    public String createAcFile() throws IOException {
        List<AccountDetails> allDetails=accountDetailsRepo.findAll();
        String filePath=file_report_url + UUID.randomUUID().toString().replace("-","");
        Path path= Paths.get(URI.create("file://"+filePath));
        try(BufferedWriter bufferedWriter=Files.newBufferedWriter(path, StandardOpenOption.CREATE_NEW)){
            for(AccountDetails accountDetails:allDetails){
                bufferedWriter.write(accountDetails.toString());
            }
        }
        return  filePath;
    }
}
