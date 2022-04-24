package com.example.discord.event;

import com.example.discord.service.DiscordTimeCounter;
import com.example.discord.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AdminMessageListener implements EventListener<MessageCreateEvent> {

    private final UserService userService;

    private final DiscordTimeCounter discordTimeCounter;

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        var eventMessage = event.getMessage();
        if (eventMessage.getAuthor().orElseThrow().isBot()) {
            return Mono.empty();
        }
        if (eventMessage.getContent().equals("Start show!")) {
            var message = "The quiz has begun!";
            discordTimeCounter.startShow();
            return Mono.just(eventMessage).flatMap(Message::getChannel)
                .flatMap(c -> c.createMessage(message)).then();
        }

        if (eventMessage.getContent().equals("Stop show!")) {
            var message = discordTimeCounter.stopShow();
            var messageWinner = userService.getWinner();
            return Mono.just(eventMessage).flatMap(Message::getChannel)
                .flatMap(c -> c.createMessage(message + messageWinner))
                .then();
        }

        if (eventMessage.getContent().equals("Clean all!")) {
            var message = userService.cleanUsers();
            return Mono.just(eventMessage).flatMap(Message::getChannel)
                .flatMap(c -> c.createMessage(message)).then();
        }
        if (eventMessage.getContent().equals("Get all!")) {
            var message = userService.getAllUsers();
            return Mono.just(eventMessage).flatMap(Message::getChannel)
                .flatMap(c -> c.createMessage(message.toString())).then();
        }
        return Mono.empty();
    }

}
