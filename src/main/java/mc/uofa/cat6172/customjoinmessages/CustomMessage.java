package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class CustomMessage {
    public TextComponent message;
    private NamedTextColor messageColor = NamedTextColor.YELLOW;
    public CustomMessage(String playerName, String message_raw, String nameColorCode){
        String[] message_components = message_raw.split("<name>");

        NamedTextColor nameColor = messageColor;

        message = Component.text(message_components[0], messageColor).append(Component.text(playerName, nameColor).append(Component.text(message_components[1], messageColor)));
    }

}
