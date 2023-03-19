package mc.uofa.cat6172.customjoinmessages;

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
        String groupColor = MessageStorage.getGroupColor(player);
        if (MessageStorage.hasJoinMessage(player.getName())){
            event.joinMessage(Component.text(MessageStorage.getJoinMessage(player.getName(), groupColor)));
        }
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        String playerName = event.getPlayer().getName();
        if (MessageStorage.hasQuitMessage(playerName)){
            event.quitMessage(Component.text(MessageStorage.getQuitMessage(playerName)));
        }
    }
}
