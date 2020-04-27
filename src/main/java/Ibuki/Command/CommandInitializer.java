package Ibuki.Command;

import Ibuki.API.IbukiCommandRegister;

public class CommandInitializer {
    public static void InitHandler(IbukiCommandRegister register) {
        register.register(new CommandPing());
    }
}
