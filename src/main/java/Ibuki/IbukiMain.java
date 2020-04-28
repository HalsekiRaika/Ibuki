package Ibuki;

import Ibuki.API.IbukiCommandHandler;
import Ibuki.API.IbukiCommandRegister;
import Ibuki.API.IbukiTokenReader;
import Ibuki.Command.CommandInitializer;
import Ibuki.Service.IbukiLoggerService;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;

import java.io.IOException;

public class IbukiMain {
    /**
     * @author ReiRokusanami
     * @Description
     * The name "Ibuki" is a combination of the name "Iarutzer" and version 2.0,
     * which was developed by the team before.
     */
    private static String TOKEN;

    public static void main(String[] args) throws IOException {
        TOKEN = new IbukiTokenReader().get();
        final DiscordClient client = DiscordClient.create(TOKEN);
        client.getEventDispatcher().on(ReadyEvent.class)
                  .subscribe(ready -> IbukiLoggerService.PrintInfo("  -- Login -- ", null, "Logged in as " + ready.getSelf().getUsername()));
        client.updatePresence(Presence.online(Activity.listening("your command")));
        IbukiCommandRegister commandHandler = new IbukiCommandHandler(client);
        CommandInitializer.InitHandler(commandHandler);
        client.login().block();
    }
}
