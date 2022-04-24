package com.example.discord.event;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GreetingMessageListener implements EventListener<MessageCreateEvent> {

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
        if (eventMessage.getContent().equals("Hi")) {
            return Mono.just(eventMessage).flatMap(Message::getChannel)
                .flatMap(c -> c.createMessage("Hello! :grinning:"))
                .then();
        }
        return Mono.empty();
    }
}
