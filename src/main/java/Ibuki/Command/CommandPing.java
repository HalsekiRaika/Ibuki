package Ibuki.Command;

import Ibuki.API.ICommandInterface;
import Ibuki.API.IbukiCommandAnnotation;

public class CommandPing implements ICommandInterface {
    @IbukiCommandAnnotation(CommandName = "ping", CommandDescription = "")
    public String onPingCommand() {
        return "Pong!";
    }
}
