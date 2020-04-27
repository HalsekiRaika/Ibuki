package Ibuki.API;

import Ibuki.Service.IbukiLoggerService;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Ibuki.EventName.IEventName.CMD_EXECUTER;

public class IbukiCommandHandler extends IbukiCommandRegister {
    public IbukiCommandHandler(DiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class).subscribe(this::MessageCreateHandler);
    }

    private void MessageCreateHandler(final MessageCreateEvent event) {
        String[] msgSplit = event.getMessage().toString().split(" ");
        String Command = msgSplit[0];
        String[] Args = argSplitter(msgSplit, Command);
        IbukiCommandScheme commandScheme = schemeHashMap.get(Command.toLowerCase());
        IbukiCommandAnnotation annotation = commandScheme.getCommandAnnotation();
        execMethod(commandScheme, event, annotation.isPrivate(), Args);
    }

    private String[] argSplitter(String[] splitMsg, String removeString) {
        List<String> Target = new ArrayList<String>(Arrays.asList(splitMsg));
        Target.remove(removeString);
        String[] args = (String[]) Target.toArray(new String[Target.size()]);
        return args;
    }

    private void execMethod(IbukiCommandScheme scheme, MessageCreateEvent event, boolean annotationSafe, Object[] param) {
        Method method = scheme.getMethod();
        Object sendObj = null;
        try {
            method.setAccessible(true);
            sendObj = method.invoke(scheme.commandInterface, param);
        } catch (Exception e) {
            IbukiLoggerService.PrintError(CMD_EXECUTER, method.getName(), "Cannot Execute Method !!");
        }
        if (sendObj != null) {
            try {
                IbukiReplyUtil.sendMessage(event, annotationSafe, sendObj.toString());
            } catch (Exception e) {};
        }
    }
}
