package com.abhi.assignment2.api.service;


import com.abhi.assignment2.api.dto.AccountDetailsDTO;
import com.abhi.assignment2.api.exception.AppAccountNotFoundException;

import java.nio.file.Path;

public interface AccountService {

    void saveFileDataToDB(Path path);

    AccountDetailsDTO get(String accountID) throws AppAccountNotFoundException;
}
