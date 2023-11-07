package com.nordeus.jobfair.auctionservice.auctionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class AuctionServiceApplication{

    public static void main(String[] args) {
        SpringApplication.run(AuctionServiceApplication.class, args);
    }

}
