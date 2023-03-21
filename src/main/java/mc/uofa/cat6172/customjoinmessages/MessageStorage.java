package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class MessageStorage {
    private static final String messageColor = "§e"; //yellow
    private static final CustomJoinMessages c = getPlugin(CustomJoinMessages.class);
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
        c.getConfig().set("JoinDB."+playerName, message);
        c.saveConfig();
    }
    public static String getJoinMessage(String playerName, String groupColorCode){
        String fallback = "ERROR: No join message exists for given player: " + playerName;
        String message = c.getConfig().getString("JoinDB." + playerName, fallback);
        message = messageColor + message;
        if (!groupColorCode.equals(messageColor) && !groupColorCode.equals("")){
            message = message.replace(playerName, "§"+groupColorCode+playerName+messageColor);
        }
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
        return c.getConfig().getString("JoinDB."+playerName) != null;
    }
    public static void setQuitMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ").replace("\\&", "§");
        c.getConfig().set("QuitDB."+playerName, message);
        c.saveConfig();
    }
    public static String getQuitMessage(String playerName, String groupColorCode){
        String fallback = "ERROR: No leave message exists for given player: " + playerName;
        String message = c.getConfig().getString("QuitDB." + playerName, fallback);
        message = messageColor + message;
        if (!groupColorCode.equals(messageColor) && !groupColorCode.equals("")){
            message = message.replace(playerName, "§"+groupColorCode+playerName+messageColor);
        }
        return message;
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
