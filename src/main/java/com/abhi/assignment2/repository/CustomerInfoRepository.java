package com.abhi.assignment2.repository;

import com.abhi.assignment2.dto.AccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerInfoRepository extends MongoRepository<AccountDTO,String> {
}
