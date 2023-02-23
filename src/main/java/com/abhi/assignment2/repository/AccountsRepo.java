package com.abhi.assignment2.repository;

import com.abhi.assignment2.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepo extends MongoRepository<Account, String> {

    List<Account> findByCustomerName(String customerName);

//    Optional<Account> findByAccountId(String accountID);

    Optional<Account> findByAccountID(String accountID);
}
