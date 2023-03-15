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
        Communication.sendConsole("Set " + playerName + "'s message to: \"" + message + "\"");
    }
    public static TextComponent getJoinMessage(String playerName){
        String fallback = "No join message exists for given player: " + playerName;
        String message = config.getString("JoinDB." + playerName, fallback);
        return Component.text(message, messageColor);
    }
    public static void removeJoinMessage(String playerName){
        config.set("JoinDB."+playerName, null);
        c.saveConfig();
        Communication.sendConsole("Removed custom join message for " + playerName);
    }
    public static Collection<String> getPlayers(){
        try{
            return config.getConfigurationSection("JoinDB").getKeys(false);
        } catch (NullPointerException e){
            return Collections.emptySet();
        }
    }
    public static boolean hasJoinMessage(String playerName){
        boolean has = config.getString("JoinDB."+playerName) != null;
        if (has) Communication.sendConsole("Found custom join message for " + playerName);
        else Communication.sendConsole("No custom join message for " + playerName);
        return has;
    }
}
