package mc.uofa.cat6172.customjoinmessages;

import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Collections;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class MessageStorage {
    private static final String messageColor = "§3";//TODO change to yellow
    private static final CustomJoinMessages c = getPlugin(CustomJoinMessages.class);
    public static void setJoinMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        c.getConfig().set("JoinDB."+playerName, message);
        c.saveConfig();
    }
    public static String getJoinMessage(String playerName, @NotNull String group){
        String fallback = "ERROR: No join message exists for given player: " + playerName;
        String message = c.getConfig().getString("JoinDB." + playerName, fallback);
        message = messageColor + message;
        return message;
    }
    public static void removeJoinMessage(String playerName){
        c.getConfig().set("JoinDB."+playerName, null);
        c.saveConfig();
    }
    public static Collection<String> getJoinPlayers(){
        try{
            return c.getConfig().getConfigurationSection("JoinDB").getKeys(false);
        } catch (NullPointerException e){
            return Collections.emptySet();
        }
    }
    public static boolean hasJoinMessage(String playerName){
        return c.getConfig().getString("QuitDB."+playerName) != null;
    }
    public static void setQuitMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        c.getConfig().set("QuitDB."+playerName, message);
        c.saveConfig();
    }
    public static String getQuitMessage(String playerName){ //TODO fix when join works
        String fallback = "ERROR: No leave message exists for given player: " + playerName;
        String message = c.getConfig().getString("QuitDB." + playerName, fallback);
        //return Component.text(message, messageColor);
        return "no leave msg";
    }
    public static void removeQuitMessage(String playerName){
        c.getConfig().set("QuitDB."+playerName, null);
        c.saveConfig();
    }
    public static Collection<String> getQuitPlayers(){
        try{
            return c.getConfig().getConfigurationSection("QuitDB").getKeys(false);
        } catch (NullPointerException e){
            return Collections.emptySet();
        }
    }
    public static boolean hasQuitMessage(String playerName){
        return c.getConfig().getString("QuitDB."+playerName) != null;
    }
}
