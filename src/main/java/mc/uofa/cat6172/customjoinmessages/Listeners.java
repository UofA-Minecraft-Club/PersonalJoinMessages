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
        TextComponent joinmessage = Component.text(playername + " has joined the game with the DEFAULT join message", NamedTextColor.YELLOW);
        event.joinMessage(joinmessage);
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        String playername = event.getPlayer().getName();
        TextComponent leavemessage = Component.text(playername + " has left the game with the DEFAULT leave message", NamedTextColor.YELLOW);
        event.quitMessage(leavemessage);
    }
}
