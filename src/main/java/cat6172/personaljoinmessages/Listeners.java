package cat6172.personaljoinmessages;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (MessageStorage.hasJoinMessage(playerName)){
            String groupColor = MessageStorage.getGroupColor(player);
            event.joinMessage(Component.text(MessageStorage.getJoinMessage(playerName, groupColor)));
        }
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (MessageStorage.hasQuitMessage(playerName)){
            String groupColor = MessageStorage.getGroupColor(player);
            event.quitMessage(Component.text(MessageStorage.getQuitMessage(playerName, groupColor)));
        }
    }
}
