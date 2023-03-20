package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collection;

public class MessageStorage {
    private static final NamedTextColor messageColor = NamedTextColor.YELLOW;
    private static final String datafolder = "custom_join_leave_messages\\";
    private static SQLHashtable joinDB;
    private static SQLHashtable quitDB;

    public static void loadMessages() throws SQLException, IOException, ClassNotFoundException {
        Files.createDirectories(Paths.get(datafolder));
        joinDB = new SQLHashtable(datafolder+"join_messages.sqlite", "join_table", datafolder+"join_messages.keys");
        quitDB = new SQLHashtable(datafolder+"leave_messages.sqlite", "leave_table", datafolder+"leave_messages.keys");
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
        try {
            quitDB.setItem(playerName, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static TextComponent getQuitMessage(String playerName){
        String message;
        String fallback = "No leave message exists for given player: " + playerName;
        try {
            message = quitDB.getItem(playerName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (message == null) message = fallback;
        return Component.text(message, messageColor);
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
