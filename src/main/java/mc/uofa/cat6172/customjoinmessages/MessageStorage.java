package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collection;

public class MessageStorage {
    private static final String datafolder = "custom_join_leave_messages\\";
    private static SQLHashtable joinDB;
    private static SQLHashtable quitDB;

    private static final String messageColor = "§e"; //yellow

    private static final CustomJoinMessages c = getPlugin(CustomJoinMessages.class);

    public static void loadMessages() throws SQLException, IOException, ClassNotFoundException {
        Files.createDirectories(Paths.get(datafolder));
        joinDB = new SQLHashtable(datafolder+"join_messages.sqlite", "join_table", datafolder+"join_messages.keys");
        quitDB = new SQLHashtable(datafolder+"leave_messages.sqlite", "leave_table", datafolder+"leave_messages.keys");
    }

    public static String getGroupColor(Player player){
        ConfigurationSection groupColors = c.getConfig().getConfigurationSection("GroupColors");
        try{
            for (String groupName: groupColors.getKeys(false)) {
                if (player.hasPermission("group."+groupName)){
                    return groupColors.getString(groupName);
                }
            }
        }
        catch (NullPointerException e){
            Communication.sendConsole("Potential error: no groups found in config. Group colors will not be applied");
            return messageColor;
        }
        return messageColor;

    }
    public static void setJoinMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        try {
            joinDB.setItem(playerName, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String getJoinMessage(String playerName, String groupColorCode){
        String fallback = "No join message exists for given player: " + playerName;
        String message;
        try {
            message = joinDB.getItem(playerName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (message == null) message = fallback;
        message = messageColor + message;
        if (!groupColorCode.equals(messageColor) && !groupColorCode.equals("")){
            message = message.replace(playerName, "§"+groupColorCode+playerName+messageColor);
        }
        return message;
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
        try {
            quitDB.setItem(playerName, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String getQuitMessage(String playerName, String groupColorCode){
        String fallback = "No leave message exists for given player: " + playerName;
        String message;
        try {
            message = quitDB.getItem(playerName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (message == null) message = fallback;
        message = messageColor + message;
        if (!groupColorCode.equals(messageColor) && !groupColorCode.equals("")){
            message = message.replace(playerName, "§"+groupColorCode+playerName+messageColor);
        }
        return message;
    }
    public static void removeQuitMessage(String playerName){
        try {
            quitDB.removeItem(playerName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Collection<String> getQuitPlayers(){
        return quitDB.keys;
    }
    public static boolean hasQuitMessage(String playerName){
        return quitDB.keys.contains(playerName);
    }
}
