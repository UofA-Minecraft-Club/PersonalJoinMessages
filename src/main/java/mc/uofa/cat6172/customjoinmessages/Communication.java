package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Communication {
    public static void sendConsole(String message){
        Bukkit.getConsoleSender().sendMessage("§3JOINMESSAGES: " + message);
    }
    public static void sendCommandSender(String message,  CommandSender reciver){
        if (reciver instanceof Player){
            reciver.sendMessage(message);
        } else {
            sendConsole(message);
        }
    }
}
