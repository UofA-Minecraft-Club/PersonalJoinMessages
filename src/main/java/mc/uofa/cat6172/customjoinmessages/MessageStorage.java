package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Collection;
import java.util.Collections;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class MessageStorage {
    private static final NamedTextColor messageColor = NamedTextColor.YELLOW;
    private static final CustomJoinMessages c = getPlugin(CustomJoinMessages.class);
    private SQLMessageStorage joinDB;
    private SQLMessageStorage leaveDB;

    public static void loadMessages(){
        joinDB = new SQLMessageStorage("join_messages.sqlite");
        leaveDB = new SQLMessageStorage("leave_messages.sqlite");
    }

    public static void setJoinMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        c.getConfig().set("JoinDB."+playerName, message);
        c.saveConfig();
    }
    public static TextComponent getJoinMessage(String playerName){
        String fallback = "ERROR: No join message exists for given player: " + playerName;
        String message = c.getConfig().getString("JoinDB." + playerName, fallback);
        return Component.text(message, messageColor);
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
        return c.getConfig().getString("JoinDB."+playerName) != null;
    }
    public static void setQuitMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        c.getConfig().set("QuitDB."+playerName, message);
        c.saveConfig();
    }
    public static TextComponent getQuitMessage(String playerName){
        String fallback = "ERROR: No leave message exists for given player: " + playerName;
        String message = c.getConfig().getString("QuitDB." + playerName, fallback);
        return Component.text(message, messageColor);
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
