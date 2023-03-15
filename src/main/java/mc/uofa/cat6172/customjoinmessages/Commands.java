package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;
        switch (args[0]) {
            case "set" -> {
                if (args.length != 3) return false;
                MessageStorage.setJoinMessage(args[1], args[2]);
                return true;
            }
            case "remove" -> {
                if (args.length != 2) return false;
                MessageStorage.removeJoinMessage(args[1]);
                return true;
            }
            case "get" -> {
                if (args.length != 2) return false;
                MessageStorage.getJoinMessage(args[1]);
                return true;
            }
            case "list" -> {
                if (args.length != 1) return false;
                Collection<String> messageOwners = MessageStorage.getPlayers();
                //TODO print to sender (console or player)
                return true;
            }
        }
        return false;
    }
}
