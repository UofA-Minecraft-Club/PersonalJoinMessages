package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class MessageStorage {
    private static final NamedTextColor messageColor = NamedTextColor.YELLOW;
    private static final CustomJoinMessages c = getPlugin(CustomJoinMessages.class);
    private static SQLHashtable joinDB;
    private static SQLHashtable leaveDB;

    public static void loadMessages() throws SQLException, IOException, ClassNotFoundException {
        joinDB = new SQLHashtable("join_messages.sqlite", "join_table", "join_messages.keys");
        leaveDB = new SQLHashtable("leave_messages.sqlite", "leave_table", "leave_messages.keys");
    }

    public static void setJoinMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        try {
            joinDB.setItem(playerName, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static TextComponent getJoinMessage(String playerName){
        String message;
        String fallback = "No join message exists for given player: " + playerName;
        try {
            message = joinDB.getItem(playerName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (message == null) message = fallback;
        return Component.text(message, messageColor);
    }
    public static void removeJoinMessage(String playerName){
        try {
            joinDB.removeItem(playerName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Collection<String> getJoinPlayers(){
        return joinDB.keys;
    }
    public static boolean hasJoinMessage(String playerName){
        return joinDB.keys.contains(playerName);
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
