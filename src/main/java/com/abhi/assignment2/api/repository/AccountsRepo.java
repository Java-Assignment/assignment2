package com.abhi.assignment2.api.repository;

import com.abhi.assignment2.api.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepo extends MongoRepository<Account, String> {

    Optional<Account> findByAccountID(String accountID);
}
