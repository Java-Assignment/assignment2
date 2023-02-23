package com.abhi.assignment2.entity;

import java.util.Random;

public enum AccountStatus {
    ACTIVE, INACTIVE;
    public  static AccountStatus generateRandomAccountStatus(){
        AccountStatus [] values=AccountStatus.values();
        int length= values().length;
        int randIndex=new Random().nextInt(length);
        return values[randIndex];
    }
}
