package cat6172.personaljoinmessages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SelfCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true; //intended to be used by players only
        boolean join = label.equals("setjoinmessage"); //else /setleavemessage because that's what this is registered to
        try{
            String playerName = sender.getName();
            StringBuilder message_temp = new StringBuilder();
            message_temp.append(args[0]);
            for (int i=1; i<args.length; i++){
                message_temp.append(" ").append(args[i]);
            }
            String message_in = message_temp.toString().replace("\\&", "§");
            String message;
            if (join){
                message = MessageStorage.setJoinMessage(playerName, message_in);
                if (message == null) Communication.sendCommandSender("§cInvalid message", sender);
                else Communication.sendCommandSender("Set your join message to: " + message, sender);
            } else{
                message = MessageStorage.setQuitMessage(playerName, message_in);
                if (message == null) Communication.sendCommandSender("§cInvalid message", sender);
                else Communication.sendCommandSender("Set your leave message to: " + message, sender);
            }
            return true;
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }
}
