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
    private static SQLiteAccess database;

    private static final String messageColor = "§e"; //yellow

    private static final CustomJoinMessages c = getPlugin(CustomJoinMessages.class);

    public static void loadMessages() throws IOException {
        Files.createDirectories(Paths.get(datafolder));
        database = new SQLiteAccess(datafolder+"message_database.sqlite", "join_leave_messages");
        Communication.sendConsole("Database successfully initialized");
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
            Communication.sendError("No groups found in config. Group colors will not be applied. Plugin will continue to function");
            return messageColor;
        }
        return messageColor;

    }
    public static void setJoinMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        try {
            database.putJoin(playerName, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String getJoinMessage(String playerName, String groupColorCode){
        String fallback = "No join message exists for given player: " + playerName;
        String message;
        try {
            message = database.getJoin(playerName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (message == null) message = fallback;
        message = messageColor + message;
        if (!groupColorCode.equals("")){
            message = message.replace(playerName, "§"+groupColorCode+playerName+messageColor);
        }
        return message;
    }
    public static void removeJoinMessage(String playerName){
        try {
            database.putJoin(playerName, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Collection<String> getJoinPlayers(){
        try {
            return database.getKeysWithNonNullJoin();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean hasJoinMessage(String playerName){
        return getJoinPlayers().contains(playerName);
    }


    public static void setQuitMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        try {
            database.putQuit(playerName, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String getQuitMessage(String playerName, String groupColorCode){
        String fallback = "No leave message exists for given player: " + playerName;
        String message;
        try {
            message = database.getQuit(playerName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (message == null) message = fallback;
        message = messageColor + message;
        if (!groupColorCode.equals("")){
            message = message.replace(playerName, "§"+groupColorCode+playerName+messageColor);
        }
        return message;
    }
    public static void removeQuitMessage(String playerName){
        try {
            database.putQuit(playerName, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Collection<String> getQuitPlayers(){
        try {
            return database.getKeysWithNonNullQuit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean hasQuitMessage(String playerName){
        return getQuitPlayers().contains(playerName);
    }

    public static int cleanup() {
        try {
            return database.deleteEntriesWithNullValues();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
