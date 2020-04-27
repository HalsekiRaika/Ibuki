package Ibuki;

import Ibuki.API.IbukiCommandHandler;
import Ibuki.API.IbukiCommandRegister;
import Ibuki.Command.CommandInitializer;
import Ibuki.Service.IbukiLoggerService;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;

public class IbukiMain {
    /**
     * @author ReiRokusanami
     * @Description
     * The name "Ibuki" is a combination of the name "Iarutzer" and version 2.0,
     * which was developed by the team before.
     */
    private static final String TOKEN = "Njk1OTkyOTI5NDE1ODU2MTcw.XoiROg.fnh3MUg5d9-N5NJZ-tSQ5gnOK8s";

    public static void main(String[] args){
        final DiscordClient client = DiscordClient.create(TOKEN);
        client.getEventDispatcher().on(ReadyEvent.class)
                  .subscribe(ready -> IbukiLoggerService.PrintInfo(" - Login - ", " - INIT - ", "Logged in as " + ready.getSelf().getUsername()));
        IbukiCommandRegister commandHandler = new IbukiCommandHandler(client);
        CommandInitializer.InitHandler(commandHandler);
        client.updatePresence(Presence.online(Activity.listening("your command")));
        client.login().block();
    }
}
