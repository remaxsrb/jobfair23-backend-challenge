package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.*;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifierLogger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {


    //auction service should have its record of users and auctions; makeshift database

    private LinkedList<User> users;
    private LinkedList<Auction> auctions;

    //This clas will be a server class with multiple threads
    //Each thread will represent a user

    //server will have its own timer which will be used by individual auctions

    private final AuctionNotifer auctionNotifer;

    //Since Lombok does not know my required initial state I will write an explicit constructor to match
    //requested app starting state

    public AuctionServiceImpl()
    {
        auctionNotifer = new AuctionNotifierLogger();
        auctions = new LinkedList<>();
        users = new LinkedList<>();
        //initial 10 active auctions

        for (int i = 0; i<10;i++)
        {
            auctions.add(new Auction(new AuctionId(), 60, 1,true, new LinkedList<>()));
        }

        //Initial 10 registered players to be joined in auctions generated above

        for (int i = 0; i<10;i++)
        {
            users.add(new User(new UserId(), 15));
            this.join(auctions.get(i).getAuctionId(), users.get(i));
        }

    }

    @Override
    public Collection<Auction> getAllActive() {

        return auctions.stream().filter(Auction::isActive).collect(Collectors.toList());
    }

    @Override
    public Auction getAuction(int auctionId) {

        return auctions.stream().filter(auction -> auction.getAuctionId().getValue()==auctionId).toList().get(0);
    }

    //getAuction method will call getAllActive method and it will use the list it returns to find a specific auction

    @Override
    public void join(AuctionId auctionId, User user) {

        this.getAuction(auctionId.getValue()).getBidders().add(user);

    }

    //join method will call the getAuction method with auctionId argument
    // and add the user to list of those who are watching the auction

    @Override
    public void bid(AuctionId auctionId, UserId userId) {
        auctionNotifer.bidPlaced(new Bid(userId, auctionId));
    }

    //method will be synchronized
    //as a new Bid object is constructed it will take in parameters passed by bid method
    // to update/check user token ballance
}
