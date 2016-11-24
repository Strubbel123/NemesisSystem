package me.strubbel.nemesis.API;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class YamlManager {
    private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("nemesis");

    private FileConfiguration customConfig = null;
    private File customConfigFile;
    private String name;

    public YamlManager (String name){
        this.name = name;
        this.customConfigFile = new File(plugin.getDataFolder(), this.name + ".yml");
        this.customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
    }

    public FileConfiguration getCustomConfig(){
        return customConfig;
    }


    public void saveCustomConfig(FileConfiguration cfg){
        customConfigFile = new File(plugin.getDataFolder(), this.name + ".yml");
        try {
            cfg.save(customConfigFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }
}
