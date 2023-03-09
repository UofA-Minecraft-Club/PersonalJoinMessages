package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        if (MessageStorage.hasCustomJoinMessage(playerName)){
            event.joinMessage(MessageStorage.getJoinMessage(playerName));
        }
    }
}
