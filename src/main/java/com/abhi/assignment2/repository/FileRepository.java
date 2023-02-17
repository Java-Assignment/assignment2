package com.abhi.assignment2.repository;

import com.abhi.assignment2.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<Account,String> {

}
