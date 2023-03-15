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
            throw new Error("Commands failed to initialize, this is likely an internal problem with plugin.yml file");
        }

        if (this.getConfig().getConfigurationSection("JoinDB") == null){
            throw new Error("Custom join messages failed to initialize, does the config file contain JoinDB section?");
        }
    }

}