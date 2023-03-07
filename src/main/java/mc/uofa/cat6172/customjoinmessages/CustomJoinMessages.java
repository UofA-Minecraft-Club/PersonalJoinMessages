package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomJoinMessages extends JavaPlugin{

    public Listeners listeners = new Listeners();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(listeners, this);
        MessageStorage.loadJoinMessages();
    }

}