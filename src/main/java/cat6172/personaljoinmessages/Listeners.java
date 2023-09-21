package cat6172.personaljoinmessages;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        String playerName = player.getName();
        String message;

        int clarity = getPlugin(PersonalJoinMessages.class).getConfig().getInt("message_identifiers");
        boolean replaceDefault = getPlugin(PersonalJoinMessages.class).getConfig().getBoolean("replace_default");

        if (MessageStorage.hasJoinMessage(playerName)){
            String groupColor = MessageStorage.getGroupColor(player);
            message = MessageStorage.getJoinMessage(playerName, groupColor);
            if ((clarity == 1 && !message.contains(playerName)) || clarity == 2) { //add if message owner is not obvious
                message = "§2[+"+playerName+"] "+message;
            }
            event.joinMessage(Component.text(message));
        } else if (replaceDefault){
            event.joinMessage(Component.text("§2[+"+playerName+"]"));
        }



    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();
        String message;

        int clarity = getPlugin(PersonalJoinMessages.class).getConfig().getInt("message_identifiers");
        boolean replaceDefault = getPlugin(PersonalJoinMessages.class).getConfig().getBoolean("replace_default");

        if (MessageStorage.hasQuitMessage(playerName)){
            String groupColor = MessageStorage.getGroupColor(player);
            message = MessageStorage.getQuitMessage(playerName, groupColor);
            if ((clarity == 1 && !message.contains(playerName)) || clarity == 2) { //add if message owner is not obvious
                message = "§c[-"+playerName+"] "+message;
            }
            event.quitMessage(Component.text(message));
        } else if (replaceDefault){
            event.quitMessage(Component.text("§c[-"+playerName+"]"));
        }
    }
}
