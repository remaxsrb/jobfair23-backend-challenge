package com.nordeus.jobfair.auctionservice.auctionservice.domain.service;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Bid;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class AuctionNotifierLogger implements AuctionNotifer {

    @Override
    public void auctionFinished(Auction auction) {
        log.info("Auction finished: {}", auction.getAuctionId().getValue());
    }

    @Override
    public void bidPlaced(Bid bid) {
        log.info("Bid placed: {}", bid.toString());
    }

    @Override
    public void activeAuctionsRefreshed(Collection<Auction> activeAuctions) {
        log.info("Active auctions are refreshed: {}", activeAuctions);
    }

    @Override
    public void insufficientTokens(User user,Auction auction) {
        log.info("User {} has insufficient tokens to bid on auction {}. ", user.getUserId().getValue(), auction.getAuctionId().getValue());
    }

    @Override
    public void userTriedToBidOnFinishedAuction(User user) {
        log.info("User {} has tried to bid on finished auction.", user.getUserId().getValue());
    }

    @Override
    public void auctionWinner(User user, Auction auction) {
        log.info("User {} has won auction {}. ", user.getUserId().getValue(), auction.getAuctionId().getValue());
    }
}
