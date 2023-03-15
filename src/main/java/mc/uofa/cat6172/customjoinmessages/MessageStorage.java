package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collection;
import java.util.Collections;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class MessageStorage {
    private static final NamedTextColor messageColor = NamedTextColor.YELLOW;
    private static final CustomJoinMessages c = getPlugin(CustomJoinMessages.class);
    private static final FileConfiguration config = c.getConfig();
    public static void setJoinMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        config.set("JoinDB."+playerName, message);
        c.saveConfig();
    }
    public static TextComponent getJoinMessage(String playerName){
        String fallback = "ERROR: No join message exists for given player: " + playerName;
        String message = config.getString("JoinDB." + playerName, fallback);
        return Component.text(message, messageColor);
    }
    public static void removeJoinMessage(String playerName){
        config.set("JoinDB."+playerName, null);
        c.saveConfig();
    }
    public static Collection<String> getPlayers(){
        try{
            return config.getConfigurationSection("JoinDB").getKeys(false);
        } catch (NullPointerException e){
            return Collections.emptySet();
        }
    }
    public static boolean hasJoinMessage(String playerName){
        return config.getString("JoinDB."+playerName) != null;
    }
}
