package com.example.discord.repository;

import java.util.ArrayList;
import java.util.List;

import discord4j.core.object.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserRepository {

    private List<User> users = new ArrayList<>();

}
