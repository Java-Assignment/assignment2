package com.abhi.assignment2.entity;

import java.util.Random;

public enum AccountType {
    HNI,WEALTH,NORMAL;
    public static AccountType generateRandomAccountType() {
        AccountType[] values = AccountType.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }

}
