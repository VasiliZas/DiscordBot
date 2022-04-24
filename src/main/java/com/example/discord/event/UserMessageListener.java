package com.example.discord.event;

import com.example.discord.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserMessageListener implements EventListener<MessageCreateEvent> {

    private final UserService userService;

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
        if (eventMessage.getContent().equals("Add me!")) {
            var user = eventMessage.getAuthor().orElseThrow();
            var message = userService.addUser(user);
            return Mono.just(eventMessage).flatMap(Message::getChannel)
                .flatMap(c -> c.createMessage(message)).then();
        }

        if (eventMessage.getContent().equals("Remove me!")) {
            var user = eventMessage.getAuthor().orElseThrow();
            var message = userService.removeUser(user);
            return Mono.just(eventMessage).flatMap(Message::getChannel)
                .flatMap(c -> c.createMessage(message)).then();
        }
        return Mono.empty();
    }
}
