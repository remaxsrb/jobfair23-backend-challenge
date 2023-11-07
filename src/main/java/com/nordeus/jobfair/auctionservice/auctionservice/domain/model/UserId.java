package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

public class UserId {

    //when a User constructor is called, a new UserId will be created and it will return a userId object with an
    // incremented static numerical value to distinguish users

    private static int createdUsers=0;


    private int userId;

    public int getValue() {
        return userId;
    }


    public UserId()
    {
        this.userId = createdUsers++;
    }


}
