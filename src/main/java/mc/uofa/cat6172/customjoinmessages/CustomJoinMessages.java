package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomJoinMessages extends JavaPlugin {
    public Listeners listeners = new Listeners();
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(listeners, this);
        try{
            MessageStorage.loadMessages();
        } catch (Exception e){
            Communication.sendConsole("Error accessing the database: " + e.getMessage());
        }
        try{
            getCommand("joinmessage").setExecutor(new Commands());
        } catch (NullPointerException e){
            throw new Error("Command joinmessage failed to initialize, this is likely an internal problem with plugin.yml file");
        }
        try{
            getCommand("leavemessage").setExecutor(new Commands());
        } catch (NullPointerException e){
            throw new Error("Command leavemessage failed to initialize, this is likely an internal problem with plugin.yml file");
        }
    }
}