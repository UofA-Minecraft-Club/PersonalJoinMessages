package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;

public class MessageStorage {
    private static final NamedTextColor messageColor = NamedTextColor.YELLOW;
    private static HashMap<String, TextComponent> joinMessages;

    public static void loadJoinMessages(){
        joinMessages = new HashMap<>();
        //load existing join messages from file
    }
    public static void setJoinMessage(String playerName, String message_raw){
        TextComponent message = Component.text(message_raw.replace("<name>", playerName), messageColor);
        joinMessages.put(playerName, message);
    }

    public static TextComponent getJoinMessage(String playerName){
        return joinMessages.get(playerName);
    }

    public static boolean hasCustomJoinMessage(String playerName){
        return joinMessages.containsKey(playerName);
    }
}
