package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try{
            switch (args[0]) {
                case "set" -> {
                    String playerName = args[1];
                    String message = args[2];
                    MessageStorage.setJoinMessage(playerName, message);
                    Communication.sendCommandSender("Set " + args[1] + "'s message to: \"" + args[2] + "\"", sender);
                    return true;
                }
                case "remove" -> {
                    String playerName = args[1];
                    MessageStorage.removeJoinMessage(playerName);
                    Communication.sendCommandSender("Removed custom join message for " + playerName, sender);
                    return true;
                }
                case "get" -> {
                    String playerName = args[1];
                    TextComponent message = MessageStorage.getJoinMessage(playerName);
                    Communication.sendCommandSender(message.content(), sender);
                    return true;
                }
                case "list" -> {
                    Collection<String> messageOwners = MessageStorage.getPlayers();
                    Communication.sendCommandSender(messageOwners.toString(), sender);
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e){
            return false;
        }
        return false;
    }
}
