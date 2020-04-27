package Ibuki.API;

import Ibuki.EventName.IEventName;
import Ibuki.Service.IbukiLoggerService;
import Ibuki.Settings;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class IbukiCommandRegister {
    public final HashMap<String, IbukiCommandScheme> schemeHashMap = new HashMap<>();
    public final List<IbukiCommandScheme> commandSchemes = new ArrayList<>();
    public void register(ICommandInterface command) {
        for (Method commandMethod : command.getClass().getMethods()) {
            IbukiCommandAnnotation annotation = commandMethod.getAnnotation(IbukiCommandAnnotation.class);
            if (annotation.CommandName().equals("")){
                IbukiLoggerService.PrintError(IEventName.CMD_REGISTER, null,
                        "Command Annotation [CommandName] must not NULL !");
                throw new IllegalArgumentException();
            }
            if (annotation.CommandDescription().equals("")) {
                IbukiLoggerService.PrintWarn(IEventName.CMD_REGISTER, null,
                        "Command Annotation [CommandDesc] is No Description.");
            }
            IbukiCommandScheme scheme = new IbukiCommandScheme(annotation, commandMethod, command);
            IbukiLoggerService.PrintInfo(IEventName.CMD_REGISTER, null,
                    "Initialization Command [ " + annotation.CommandName() + " ]");
            schemeHashMap.put(Settings.PREFIX + annotation.CommandName(), scheme);
            commandSchemes.add(scheme);
        }
    }
}

