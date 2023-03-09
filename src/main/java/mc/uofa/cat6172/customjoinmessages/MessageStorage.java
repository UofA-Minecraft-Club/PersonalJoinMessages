package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;

public class MessageStorage {
    private static final NamedTextColor messageColor = NamedTextColor.YELLOW;
    private static HashMap<String, String> joinMessages;

    public static void loadJoinMessages(){
        joinMessages = new HashMap<>();
        //TODO: load existing join messages from file
        Communication.sendConsole("Loaded " + joinMessages.size() + " custom messages from file");
    }
    public static void setJoinMessage(String playerName, String message_raw){
        String message = message_raw.replace("_", " ");
        joinMessages.put(playerName, message);
        Communication.sendConsole("Set " + playerName + "'s message to: \"" + message + "\"");
    }
    public static TextComponent getJoinMessage(String playerName){
        return Component.text(joinMessages.get(playerName).replace("<name>", playerName), messageColor);
    }
    public static void removeJoinMessage(String playerName){
        joinMessages.remove(playerName);
        Communication.sendConsole("Removed custom join message for " + playerName);
    }

    public static void listJoinMessages(){
        Communication.sendConsole("Custom messages availabe for: " + joinMessages.keySet().toString());
    }

    public static boolean hasCustomJoinMessage(String playerName){
        boolean has = joinMessages.containsKey(playerName);
        if (has) Communication.sendConsole("Found custom join message for " + playerName);
        else{
            Communication.sendConsole("No custom join message for " + playerName);
        }
        return has;
    }
}
