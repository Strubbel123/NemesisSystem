package me.strubbel.nemesis.API;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class YamlManager {
  private Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("nemesissystem");
  private FileConfiguration customConfig = null;
  private File customConfigFile;
  private String name;

  public YamlManager(String name) {
    this.name = name;
    this.customConfigFile = new File(this.plugin.getDataFolder(), this.name + ".yml");
    this.customConfig = YamlConfiguration.loadConfiguration((File) this.customConfigFile);
  }

  public FileConfiguration getCustomConfig() {
    return this.customConfig;
  }

  public void saveCustomConfig(FileConfiguration cfg) {
    this.customConfigFile = new File(this.plugin.getDataFolder(), this.name + ".yml");
    try {
      cfg.save(this.customConfigFile);
    } catch (IOException ex) {
      Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + this.customConfigFile, ex);
    }
  }

  public void deleteCustomConfig() {
    this.customConfigFile.delete();
  }
}
