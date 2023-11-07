package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;


public class Bid {

    //Bid has to have a timestamp so the server can determine which player placed the bit first

    private UserId userId;
    private AuctionId auctionId;

    public Bid(UserId userId, AuctionId auctionId)
    {
        this.userId=userId;
        this.auctionId = auctionId;
    }



}
