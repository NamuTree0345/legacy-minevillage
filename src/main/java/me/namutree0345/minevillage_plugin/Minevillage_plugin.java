package me.namutree0345.minevillage_plugin;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Minevillage_plugin extends JavaPlugin {

    public static Configuration configuration;

    @Override
    public void onEnable() {
        try {
            configuration = new Configuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServer().getPluginManager().registerEvents(new EmeraldCollector(), this);
        getServer().getPluginManager().registerEvents(new Extensions(), this);
        PluginCommand npces = getCommand("npces");
        npces.setExecutor(new NpcesCommand());
        npces.setTabCompleter(new NpcesCommandTabCompleter());
    }
}
