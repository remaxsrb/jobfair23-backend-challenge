package com.nordeus.jobfair.auctionservice.auctionservice.api;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.AuctionService;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/auctions")
public class HttpController {

    private AuctionService auctionService;

    @GetMapping("/active")
    public Collection<Auction> getAllActive() {
        return auctionService.getAllActive();
    }

    @GetMapping("/{auctionId}")
    public Auction getAuction(@PathVariable int auctionId) {return auctionService.getAuction(auctionId);}

    @PostMapping("/{userId}/{auctionId}")
    public void bid(@PathVariable int userId, @PathVariable int auctionId) {auctionService.bid(auctionId,userId);}


}
