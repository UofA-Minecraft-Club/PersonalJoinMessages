package mc.uofa.cat6172.customjoinmessages;

import mc.uofa.cat6172.customjoinmessages.MessageStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;
        if (args[0] == "set") {
            if (args.length != 3) return false;
            MessageStorage.setJoinMessage(args[1], args[2]);
            return true;
        } else if (args[0] == "remove") {
            if (args.length != 2) return false;
            MessageStorage.removeJoinMessage(args[1]);
            return true;
        } else if (args[0] == "list"){
            MessageStorage.listJoinMessages();
            return true;
        }
        return false;
    }
}
