package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        if (MessageStorage.hasJoinMessage(playerName)){
            event.joinMessage(MessageStorage.getJoinMessage(playerName));
        }
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        String playerName = event.getPlayer().getName();
        if (MessageStorage.hasQuitMessage(playerName)){
            event.quitMessage(MessageStorage.getQuitMessage(playerName));
        }
    }
}
