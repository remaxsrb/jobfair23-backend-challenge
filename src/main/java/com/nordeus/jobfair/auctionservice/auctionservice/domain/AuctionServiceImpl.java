package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.*;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifer;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifierLogger;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService, Runnable{


    //auction service should have its record of users and auctions; makeshift database

    private LinkedList<User> users;
    private LinkedList<Auction> auctions;

    private final AuctionNotifer auctionNotifer;

    //Since Lombok does not know my required initial state I will write an explicit constructor to match
    //requested app starting state

    private void generateAuctions() {

        for (int i = 0; i < 10; i++)
        {
            auctions.add(new Auction(new AuctionId(), 60, 1,true, new LinkedList<>()));
            this.join(auctions.getLast().getAuctionId(), users.get(i));
        }
    }

    private void generateUsers()
    {
        for (int i = 0; i < 10; i++)
            users.add(new User(new UserId(), 5));
    }

    public AuctionServiceImpl()
    {
        auctionNotifer = new AuctionNotifierLogger();
        auctions = new LinkedList<>();
        users = new LinkedList<>();


        this.generateUsers();
        //Initial 10 registered players to be joined in auctions generated below
        this.generateAuctions();

    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void run() {

        synchronized (this)
        {
            long start = System.nanoTime()/1000000000;

            while(true) //Infinite loop makes sure that new auctions will always be generated
            {
                for (Auction auction : auctions) {
                    auction.tick();

                    //Mark auction as finished and declare a winner
                    if (auction.getDuration() == 0) {
                        User user = auction.getBidders().getLast();
                        auctionNotifer.auctionWinner(user, auction);

                        auction.setActive(false);
                        auctionNotifer.auctionFinished(auction);
                    }
                }

                try {
                    this.wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                long end = System.nanoTime()/1000000000;


                if ((int)end-(int)start == 60) {
                    this.generateAuctions();
                    auctionNotifer.activeAuctionsRefreshed(getAllActive());
                    start = System.nanoTime()/1000000000;

                };

            }
        }

    }

    @Override
    public Collection<Auction> getAllActive() {

        return auctions.stream().filter(Auction::isActive).collect(Collectors.toList());
    }

    //getAuction method will call getAllActive method, and it will use the list it returns to find a specific auction


    @Override
    public Auction getAuction(int auctionId) {

        return auctions.stream().filter(auction -> auction.getAuctionId().getValue()==auctionId).toList().get(0);
    }

    @Override
    public User getUser(int userId) {
        return users.stream().filter(user -> user.getUserId().getValue()==userId).toList().get(0);
    }


    @Override
    public void join(AuctionId auctionId, User user) {

        this.getAuction(auctionId.getValue()).getBidders().add(user);

    }

    //join method will call the getAuction method with auctionId argument
    // and add the user to list of those who are watching the auction

    @Override
    public void bid(int auctionId, int userId) {

        Auction auction = getAuction(auctionId);
        User user = getUser(userId);

        if(!auction.isActive()) {
            auctionNotifer.userTriedToBidOnFinishedAuction(user);
            return;
        }

        if(user.getTokenBalance() < auction.getPlayerPrice())
        {
            auctionNotifer.insufficientTokens(user, auction);
            return;
        }

        auction.incrementPlayerPrice();
        user.decrementTokenBalance();
        auction.addBidder(user);

        if(auction.getDuration() < 5) auction.setDuration(5);

        auctionNotifer.bidPlaced(new Bid(user.getUserId(), auction.getAuctionId()));

    }

    //Before a new Bid object is constructed it will take in parameters passed by bid method
    // to update/check user token balance and if auction is active or not
}

/*
*
* At first, my idea was to implement a server-client application with multiple threads.
* AuctionService would behave as a server while clients would be users which are biding for players.
* When a server timer ticks one minute it would generate another 10 auctions and prolong active ones if needed.
*
* Bid method would be synchronized of course.
*
* I've concluded that I shouldn't do that since it would require creating and running multiple (depending on user count) java applications simultaneously.
* And that does not seem to be what is required of us participants in this challenge.
*
* All user actions would be processed as POST requests tested through POSTMAN.
* This way, it will be guaranteed that method bid will be called by one 'user' at the time,
* avoiding potential race hazards which are present in actual top11 game.
*
* Still, AuctionServiceImpl will also implement a Runnable interface to provide requested periodic behaviour.
*
* */
