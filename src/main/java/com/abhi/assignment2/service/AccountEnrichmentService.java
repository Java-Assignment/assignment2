package com.abhi.assignment2.service;

import com.abhi.assignment2.entity.AccountEnrichment;
import com.abhi.assignment2.exception.AppAccountNotFoundException;

public interface AccountEnrichmentService {
    AccountEnrichment getByAccountID(String accountID) throws AppAccountNotFoundException;
}
