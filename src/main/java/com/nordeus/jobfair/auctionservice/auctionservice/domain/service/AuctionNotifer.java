package com.nordeus.jobfair.auctionservice.auctionservice.domain.service;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.User;

import java.util.Collection;

public interface AuctionNotifer {

    void auctionFinished(Auction auction);

    void bidPlaced(Bid bid);

    void activeAuctionsRefreshed(Collection<Auction> activeAuctions);

    //added log methods

    void insufficientTokens(User user, Auction auction);

    void userTriedToBidOnFinishedAuction(User user);

    void auctionWinner(User user, Auction auction);
}
