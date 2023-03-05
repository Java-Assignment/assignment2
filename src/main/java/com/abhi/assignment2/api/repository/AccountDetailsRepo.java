package com.abhi.assignment2.api.repository;

import com.abhi.assignment2.api.entity.AccountDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountDetailsRepo extends MongoRepository<AccountDetails,String> {
}
