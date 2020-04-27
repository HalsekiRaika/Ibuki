package Ibuki.Service;

import reactor.util.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author ReiRokusanami
 * @URL https://github.com/ReiRokusanami0010/Log5RLibs
 * @Description
 * This is an improvement of Logger, Log5RLibs, which I am developing in C#, to Java (for Ibuki).
 */

public class IbukiLoggerService {
    private static final Object _lockObject = new Object();
    public static Calendar calendar = Calendar.getInstance();
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd | HH/mm/ss.SSS");
    private static void printlog(String eventName, String levelName, @Nullable String commandName, String message){
        synchronized (_lockObject) {
            System.out.printf("[ %-15s ] ", sdf.format(calendar.getTime()));
            System.out.printf("[ %-15s ] ", eventName);
            System.out.printf("[ %-15s ] ", levelName);
            if (!(commandName == null)) { System.out.printf("[ %15s ] " , commandName); }
            System.out.println(message);
        }
    }

    public static void PrintInfo(String eventName, @Nullable String commandName, String message) {
        printlog(eventName, "Information", commandName, message);
    }

    public static void PrintCaut(String eventName, @Nullable String commandName, String message) {
        printlog(eventName, "Caution", commandName, message);
    }

    public static void PrintWarn(String eventName, @Nullable String commandName, String message) {
        printlog(eventName, "Warning", commandName, message);
    }

    public static void PrintError(String eventName, @Nullable String commandName, String message) {
        printlog(eventName, "! Error !", commandName, message);
    }
}
