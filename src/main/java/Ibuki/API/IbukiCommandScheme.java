package Ibuki.API;

import java.lang.reflect.Method;

public class IbukiCommandScheme {
    final IbukiCommandAnnotation CommandAnnotation;
    final Method method;
    final ICommandInterface commandInterface;

    public IbukiCommandScheme(IbukiCommandAnnotation commandAnnotation, Method method, ICommandInterface commandInterface){
        this.CommandAnnotation = commandAnnotation;
        this.method = method;
        this.commandInterface = commandInterface;
    }

    public IbukiCommandAnnotation getCommandAnnotation() {
        return CommandAnnotation;
    }

    public Method getMethod() {
        return method;
    }

    public ICommandInterface getCommandInterface() {
        return commandInterface;
    }
}
