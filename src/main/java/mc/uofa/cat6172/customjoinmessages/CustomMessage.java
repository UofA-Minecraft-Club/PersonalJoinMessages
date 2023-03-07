package mc.uofa.cat6172.customjoinmessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class CustomMessage {
    private String playername = "";
    private String prefix = "";
    private String postfix;
    private NamedTextColor messageColor = NamedTextColor.YELLOW;
    private NamedTextColor namecolor = messageColor;

    public CustomMessage(String playername, boolean join){
        this.playername = playername;
        if (join) postfix = " has joined the game";
        else postfix = " has left the game";
    }
    public TextComponent getMessage(){
        return Component.text(prefix, messageColor).append(Component.text(playername, namecolor).append(Component.text(postfix, messageColor)));
    }
}
