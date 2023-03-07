package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomJoinMessages extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playername = event.getPlayer().getName();
        TextComponent joinmessage = Component.text(playername + " has joined the game with the DEFAULT join message", NamedTextColor.YELLOW);
        if (playername.equals("Cat6172")){
            joinmessage = Component.text(playername + " has joined the game with a SPECIAL join message", NamedTextColor.YELLOW);
        }

        event.joinMessage(joinmessage);
    }

}