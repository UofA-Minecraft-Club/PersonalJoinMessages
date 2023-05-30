package mc.uofa.cat6172.customjoinmessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomJoinMessages extends JavaPlugin {
    public final Listeners listeners = new Listeners();
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(listeners, this);
        this.saveDefaultConfig();
        checkErrors();
        cleanup();
    }

    private void checkErrors() {
        Communication.sendConsole("Initializing commands...");
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
        Communication.sendConsole("Initializing groups...");
        if (this.getConfig().getConfigurationSection("GroupColors") == null){
            throw new Error("Group colors failed to initialize, does the config file contain GroupColors section?");
        }
        Communication.sendConsole("Accessing database...");
        try{
            MessageStorage.loadMessages();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void cleanup() {
        int rowsDeleted = MessageStorage.cleanup();
        if (rowsDeleted > 0) Communication.sendConsole("Removed " + rowsDeleted + " null rows from the database");
    }

}