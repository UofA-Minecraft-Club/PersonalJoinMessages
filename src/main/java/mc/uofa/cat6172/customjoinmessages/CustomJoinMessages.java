package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomJoinMessages extends JavaPlugin {
    public Listeners listeners = new Listeners();
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(listeners, this);
        this.saveDefaultConfig();
        checkErrors();
    }

    private void checkErrors() {
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
        if (this.getConfig().getConfigurationSection("GroupColors") == null){
            throw new Error("Group colors failed to initialize, does the config file contain GroupColors section?");
        }
        try{
            MessageStorage.loadMessages();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}