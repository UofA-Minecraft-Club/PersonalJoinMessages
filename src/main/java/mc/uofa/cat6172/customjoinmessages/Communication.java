package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.Bukkit;

public class Communication {
    public static void sendConsole(String message){
        Bukkit.getConsoleSender().sendMessage("JOINMESSAGES: " + message);
    }
}
