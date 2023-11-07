package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.User;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.UserId;

import java.util.Collection;

public interface AuctionService {

    Collection<Auction> getAllActive();

    Auction getAuction(int auctionId); //I changed this argument type so that I can use REST integer parameter

    User getUser(int userId);

    void join(AuctionId auctionId, User user);

    void bid(int auctionId, int userId); //I changed arguments type so that I can use REST integer parameter
}
