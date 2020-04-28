package Ibuki.API;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.TextChannel;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;

public class IbukiReplyUtil {
    public static void sendMessage(MessageCreateEvent event, boolean annotationSafe, String sendObject) {
        Mono<MessageChannel> channel = event.getMessage().getChannel().cache();
        if (isPrivate(channel) && annotationSafe) {
            channel.ofType(PrivateChannel.class).flatMap(privateChannel -> privateChannel.createMessage(sendObject));
        } else {
            channel.ofType(TextChannel.class).flatMap(textChannel -> textChannel.createMessage(sendObject));
        }
    }

    private static boolean isPrivate(Mono<MessageChannel> channel){
        AtomicBoolean Switch = new AtomicBoolean(false);
        channel.ofType(TextChannel.class)
                .switchIfEmpty(channel.ofType(PrivateChannel.class)
                        .flatMap(privateChannel -> {
                            Switch.set(true);
                            return null;
                        })
                        .then(Mono.empty()))
                .flatMap(textChannel -> {
                    Switch.set(false);
                    return null;
                });
        return Switch.get();
    }
}
