package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.Bukkit;

public class Communication {
    public static void sendConsole(String message){
        Bukkit.getConsoleSender().sendMessage("§3JOINMESSAGES: " + message);
    }

    //TODO make all of these send from command and add sender as argument
}
