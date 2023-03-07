package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class CustomJoinMessages extends JavaPlugin{

    public Listeners listeners = new Listeners();

    public HashMap<String, CustomMessage> joinMessages;
    public HashMap<String, CustomMessage> leaveMessages;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(listeners, this);
    }



}