package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

import java.util.HashMap;



public class MessageStorage {
    private static final NamedTextColor messageColor = NamedTextColor.YELLOW;
    private static final CustomJoinMessages c = getPlugin(CustomJoinMessages.class);
    private static final FileConfiguration config = c.getConfig();
    private static HashMap<String, String> joinMessages;

    public static void loadJoinMessages(){
        joinMessages = new HashMap<>();
        for (String key: config.getConfigurationSection("JoinDB").getKeys(false)){
            joinMessages.put(key, config.getString("JoinDB."+key));
        }
        Communication.sendConsole("Loaded " + joinMessages.size() + " custom messages from file");
    }
    public static void setJoinMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ");
        joinMessages.put(playerName, message);
        config.set("JoinDB."+playerName, message);
        c.saveConfig();
        Communication.sendConsole("Set " + playerName + "'s message to: \"" + message + "\"");
    }
    public static TextComponent getJoinMessage(String playerName){
        return Component.text(joinMessages.get(playerName).replace("<name>", playerName), messageColor);
    }
    public static void removeJoinMessage(String playerName){
        joinMessages.remove(playerName);
        config.set("JoinDB."+playerName, null);
        c.saveConfig();
        Communication.sendConsole("Removed custom join message for " + playerName);
    }

    public static void listJoinMessages(){
        Communication.sendConsole("Custom messages availabe for: " + joinMessages.keySet());
    }

    public static boolean hasCustomJoinMessage(String playerName){
        boolean has = joinMessages.get(playerName) != null;
        if (has) Communication.sendConsole("Found custom join message for " + playerName);
        else Communication.sendConsole("No custom join message for " + playerName);
        return has;
    }
}
