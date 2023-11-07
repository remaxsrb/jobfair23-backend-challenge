package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

public class AuctionId {

    //when an Auction constructor is called, a new AuctionId will be created and it will return a AuctionId object with an
    // incremented static numerical value to distinguish auctions

    private static int createdAuctions=0;

    private int auctionId;

    public int getValue() {
        return auctionId;
    }


    public AuctionId () {
        auctionId = createdAuctions++;
    }
}
