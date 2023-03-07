package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playername = event.getPlayer().getName();
        CustomMessage joinmessage = new CustomMessage(playername, "<name> has joined the game", "idk");
        event.joinMessage(joinmessage.message);
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        String playername = event.getPlayer().getName();
        CustomMessage leavemessage = new CustomMessage(playername, "<name> has left the game", "idk");
        event.quitMessage(leavemessage.message);
    }
}
