package com.example.discord.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DiscordTimeCounter {

    private long start;

    public void startShow(){
        start = new Date().getTime();
    }

    public String stopShow(){
        if (start == 0) {return "First you need to start the quiz! :point_up_2: ";}
        long time = new Date().getTime() - start;
        long minutes = time / 60000 % 60;
        long sec = time  /1000 % 60;
        start = 0;
        System.out.println(time);
     return String.format("The quiz is over! It lasted %d min %d sec. ",minutes,sec);
    }
}
