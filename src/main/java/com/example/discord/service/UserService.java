package com.example.discord.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import com.example.discord.repository.UserRepository;
import discord4j.core.object.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String removeUser(User user) {
        if (!userRepository.getUsers().contains(user)) {
            return "You were not on the participant list.";
        }
        userRepository.getUsers().remove(user);
        return "You have been removed from the member list.";
    }

    public String cleanUsers() {
        userRepository.getUsers().clear();
        return "The list of participants has been cleared.";
    }

    public String addUser(User user) {
        if (userRepository.getUsers().contains(user)) {
            return "You have already been added to the list of participants.";
        }
        userRepository.getUsers().add(user);
        return "You have been added to the list of participants.";
    }

    public List<String> getAllUsers() {
        if (!userRepository.getUsers().isEmpty()) {
            return userRepository.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
        }
        return List.of("Empty");
    }

    public String getWinner() {
        if (userRepository.getUsers().isEmpty()) {
            return " There can be no winner where no one participates! :point_up_2: ";
        }
        SecureRandom secureRandom = new SecureRandom();
        int count = secureRandom.nextInt(userRepository.getUsers().size());
        User user = userRepository.getUsers().get(count);
        return String.format(" The quiz winner is user number %d with name %s. :mechanical_arm:  ", count,
            user.getUsername());
    }
}
