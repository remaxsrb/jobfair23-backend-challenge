package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

@Getter
@Setter
@AllArgsConstructor
public class Auction {

    private final AuctionId auctionId;

    private int duration; //stored in seconds but can be represented in minutes

    private int playerPrice; //initial player price in tokens

    private boolean active;

    private LinkedList<User> bidders; //players which have joined the auction

    public void incrementPlayerPrice() {this.playerPrice++;}

    public void addBidder(User user) {this.bidders.add(user);}

    public void tick() {this.duration--;}

    @Override
    public String toString() {
        return "Auction " + this.getAuctionId().getValue();
    }
}
