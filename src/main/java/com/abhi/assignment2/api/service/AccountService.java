package com.abhi.assignment2.api.service;


import com.abhi.assignment2.api.entity.Account;

import java.nio.file.Path;

public interface AccountService {

    void saveFileDataToDB(Path path);

    Account get(String accountID);
}
