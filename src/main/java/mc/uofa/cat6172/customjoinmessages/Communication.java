package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class Communication {
    public static void sendConsole(String message){
        getPlugin(CustomJoinMessages.class).getLogger().info(message);
    }
    public static void sendError(String message){
        getPlugin(CustomJoinMessages.class).getLogger().warning(message);
    }
    public static void sendCommandSender(String message,  CommandSender receiver){
        if (receiver instanceof Player){
            receiver.sendMessage(message);
        } else {
            Bukkit.getConsoleSender().sendMessage(message);
        }
    }
}
