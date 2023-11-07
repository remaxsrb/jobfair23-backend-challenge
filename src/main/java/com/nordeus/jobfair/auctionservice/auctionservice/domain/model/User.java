package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {



    private final UserId userId;

    private int tokenBalance;

    private void decrementTokenBalance() {
        this.tokenBalance--;
    }

    public int getUserId() {
        return userId.getValue();
    }

}
