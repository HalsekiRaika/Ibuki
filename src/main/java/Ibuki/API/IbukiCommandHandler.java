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

    private static String[] Args = new String[]{};

    private void MessageCreateHandler(final MessageCreateEvent event) {
        String[] msgSplit = event.getMessage().getContent().get().split(" ");
        String Command = msgSplit[0];
        if (!isCommandOnly(msgSplit)) {
            Args = argSplitter(msgSplit, Command);
        }
        IbukiCommandScheme commandScheme = schemeHashMap.get(Command.toLowerCase());
        IbukiCommandAnnotation annotation = commandScheme.getCommandAnnotation();
        execMethod(commandScheme, event, annotation.isPrivate(), Args);
    }

    private boolean isCommandOnly(String[] target) {
        if (target.length == 1) { return true; }
        else { return false; }
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
                IbukiLoggerService.PrintInfo(CMD_EXECUTER, scheme.getCommandAnnotation().CommandName(), "Execute Command");
                IbukiLoggerService.PrintArray(CMD_EXECUTER, scheme.getCommandAnnotation().CommandName(), Fix(event.getMessage().getAuthor().get().toString()));
                IbukiReplyUtil.sendMessage(event, annotationSafe, sendObj.toString());
            } catch (Exception e) {};
        }
    }

    private static String[] Fix(String target) {
        String[] buf = target.split(", ");
        return buf;
    }
}
