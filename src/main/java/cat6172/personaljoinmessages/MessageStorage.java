package cat6172.personaljoinmessages;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class MessageStorage {
    public static String datafolder;
    private static DatabaseAccess database;

    private static final String messageColor = "§e"; //yellow

    private static final PersonalJoinMessages c = getPlugin(PersonalJoinMessages.class);

    public static void loadMessages() throws IOException {
        String connString = "jdbc:sqlite:" + datafolder + System.getProperty("file.separator") + "message_database.sqlite";
        database = new SQLiteAccess(connString, "join_leave_messages");
        Communication.sendConsole("Database successfully initialized");
    }

    public static String getGroupColor(Player player){
        ConfigurationSection groupColors = c.getConfig().getConfigurationSection("GroupColors");
        try{
            for (String groupName: groupColors.getKeys(false)) {
                if (player.hasPermission("group."+groupName)){
                    return "§"+groupColors.getString(groupName);
                }
            }
        }
        catch (NullPointerException e){
            Communication.sendError("No groups found in config. Group colors will not be applied. Plugin will continue to function");
            return messageColor;
        }
        return messageColor;

    }


    public static String setJoinMessage(String playerName, String message){
        if (hasJoinMessage(playerName)) removeJoinMessage(playerName); // fix to a problem that shouldn't happen.
        try {
            database.putJoin(playerName, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
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
            message = message.replace(playerName, groupColorCode+playerName+messageColor);
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


    public static String setQuitMessage(String playerName, String message){

        if (hasQuitMessage(playerName)) removeQuitMessage(playerName);
        try {
            database.putQuit(playerName, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
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
            message = message.replace(playerName, groupColorCode+playerName+messageColor);
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

    public static void close(){
        database.close();
        Communication.sendConsole("Safely closing Joinmessages database");
    }
}
