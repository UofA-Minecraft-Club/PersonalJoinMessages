package cat6172.personaljoinmessages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class AdminCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        boolean join = label.equals("joinmessage"); //else /leavemessage because that's what this is registered to
        try{
            switch (args[0]) {
                case "set" -> {
                    String playerName = args[1];
                    StringBuilder message_temp = new StringBuilder();
                    message_temp.append(args[2]);
                    for (int i=3; i<args.length; i++){
                        message_temp.append(" ").append(args[i]);
                    }
                    String message_in = message_temp.toString().replace("\\&", "§");
                    String message;
                    if (join){
                        message = MessageStorage.setJoinMessage(playerName, message_in);
                        if (message == null) Communication.sendCommandSender("§cInvalid message", sender);
                        else Communication.sendCommandSender("Set " + args[1] + "'s join message to: \"" + message + "\"", sender);
                    } else{
                        message = MessageStorage.setQuitMessage(playerName, message_in);
                        if (message == null) Communication.sendCommandSender("§cInvalid message", sender);
                        else Communication.sendCommandSender("Set " + args[1] + "'s leave message to: \"" + message + "\"", sender);
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
                    else message = MessageStorage.getQuitMessage(playerName, "");
                    Communication.sendCommandSender("Message: "+message, sender);
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
                    getPlugin(PersonalJoinMessages.class).reloadConfig();
                    Communication.sendCommandSender("Reloaded all join/leave message config", sender);
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e){
            return false;
        }
        return false;
    }
}
