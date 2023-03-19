package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        boolean join = label.equals("joinmessage"); //else /leavemessage because that's what this is registered to
        try{
            switch (args[0]) {
                case "set" -> {
                    String playerName = args[1];
                    String message = args[2];
                    if (join){
                        MessageStorage.setJoinMessage(playerName, message);
                        Communication.sendCommandSender("Set " + args[1] + "'s join message to: \"" + args[2] + "\"", sender);
                    } else{
                        MessageStorage.setQuitMessage(playerName, message);
                        Communication.sendCommandSender("Set " + args[1] + "'s leave message to: \"" + args[2] + "\"", sender);
                    }
                    return true;
                }
                case "remove" -> {
                    String playerName = args[1];
                    if (join){
                        MessageStorage.removeJoinMessage(playerName);
                        Communication.sendCommandSender("Removed custom join message for " + playerName, sender);
                    }
                    else {
                        MessageStorage.removeQuitMessage(playerName);
                        Communication.sendCommandSender("Removed custom leave message for " + playerName, sender);
                    }
                    return true;
                }
                case "get" -> {
                    String playerName = args[1];
                    String message;
                    if (join) message = MessageStorage.getJoinMessage(playerName, "");
                    else message = MessageStorage.getQuitMessage(playerName);
                    Communication.sendCommandSender(message, sender);
                    return true;
                }
                case "list" -> {
                    Collection<String> messageOwners;
                    if (join) messageOwners = MessageStorage.getJoinPlayers();
                    else messageOwners = MessageStorage.getQuitPlayers();
                    Communication.sendCommandSender(messageOwners.toString(), sender);
                    return true;
                }
                case "reload" -> {
                    getPlugin(CustomJoinMessages.class).reloadConfig();
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e){
            return false;
        }
        return false;
    }
}
