package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    public void extendAuctionDuration() {this.duration = 5;}
}
